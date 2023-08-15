import io
import os
import sys
from lxml import etree
from sickle import Sickle

if len(sys.argv) == 1:
    print('Please give a directory name')
    exit()
directory = sys.argv[1]
print('saving to %s' % (directory))
if not os.path.exists(directory):
    os.makedirs(directory)

namespaces = {
    'oai': 'http://www.openarchives.org/OAI/2.0/',
    'marc21': 'http://www.loc.gov/MARC21/slim'
}

def write_output(output, i, directory):
    """Writes MARC records to file""" 
    file_name = "%s/%06d.xml" % (directory, i)
    print(file_name)
    with io.open(file_name, 'w', encoding='utf8') as f:
        # f.write(header)
        f.write("\n".join(output) + "\n")
        # f.write(footer)

#                http://services.kb.nl/mdo/oai?verb=ListIdentifiers&set=GGC&metadataPrefix=mdoall
sickle = Sickle('http://services.kb.nl/mdo/oai', max_retries=4)

header = '<?xml version="1.0" encoding="utf8"?>' + "\n" + '<records>' + "\n" 
footer = '</records>'

output = []
prev_token = None
i = 0
record_count = 0
it = sickle.ListIdentifiers(metadataPrefix='mdoall', set='GGC', ignore_deleted=True) # ignore_deleted=True, picaplus
for record in it:
    record_count = record_count + 1
    if it.resumption_token != prev_token:
        print(it.resumption_token)
        prev_token = it.resumption_token

    tree = etree.ElementTree(record.xml)
    id = tree.xpath('/oai:header[1]/oai:identifier[1]/text()', namespaces=namespaces)
    output.append(id[0])

    #  core = etree.tostring(record, encoding='utf8', method='xml').decode("utf-8")
    #  output.append(core.replace("<?xml version='1.0' encoding='utf8'?>\n", ''))
    # output.append(etree.tostring(record, encoding='utf8', method='xml').decode("utf-8"))
    # tree = etree.ElementTree(record.xml)
    # token = tree.xpath('/ListRecords/resumptionToken[0]', namespaces=namespaces)
    # print(token)
    # print(etree.tostring(token, encoding='utf8', method='xml').decode("utf-8"))

    # recs = tree.xpath('/oai:record[*]/oai:metadata/marc21:record', namespaces=namespaces)
    # for rec in recs:
    #     core = etree.tostring(rec, encoding='utf8', method='xml').decode("utf-8")
    #     output.append(core.replace("<?xml version='1.0' encoding='utf8'?>\n", ''))
    if len(output) >= 100:
        write_output(output, i, directory)
        output = []
        i = i + 1

write_output(output, i, directory)
print('savied %d records to %d files' % (record_count, i))

