import io
import sys
from lxml import etree
from sickle import Sickle

if len(sys.argv) == 1:
    print('Please give a directory name')
    exit()
dir = sys.argv[1]
print('saving to %s' % (dir))

namespaces = {
    'oai': 'http://www.openarchives.org/OAI/2.0/',
    'marc21': 'http://www.loc.gov/MARC21/slim'
} 

sickle = Sickle('https://eu02.alma.exlibrisgroup.com/view/oai/43ACC_ONB/request', max_retries=4)

header = '<?xml version="1.0" encoding="utf8"?>' + "\n" + '<records>' + "\n" 
footer = '</records>'

output = []
i = 0
record_count = 0
it = sickle.ListRecords(metadataPrefix='marc21', set='FULLMARC')
for record in it:
    tree = etree.ElementTree(record.xml)
    token = tree.xpath('/resumptionToken', namespaces=namespaces)
    
    recs = tree.xpath('/oai:record[*]/oai:metadata/marc21:record', namespaces=namespaces)
    for rec in recs:
        core = etree.tostring(rec, encoding='utf8', method='xml').decode("utf-8")
        output.append(core.replace("<?xml version='1.0' encoding='utf8'?>\n", ''))
        record_count += 1
    if len(output) >= 1000:
        write_output(output, i, dir)
        output = []
        i = i + 1

write_output(output, i, dir)
print('savied %d records to %d files' % (record_count, i))

def write_output(output, i, dir):
    """Writes MARC records to file""" 
    file_name = "%s/%06d.xml" % (dir, i)
    print(file_name)
    with io.open(file_name, 'w', encoding='utf8') as f:
        f.write(header)
        f.write("\n".join(output) + "\n")
        f.write(footer)
