from lxml import etree
import io
from sickle import Sickle

namespaces = {
    'oai': 'http://www.openarchives.org/OAI/2.0/',
    'marc21': 'http://www.loc.gov/MARC21/slim'
} 

sickle = Sickle('https://eu02.alma.exlibrisgroup.com/view/oai/43ACC_ONB/request')

header = '<?xml version="1.0" encoding="utf8"?>' + "\n" + '<records>' + "\n" 
footer = '</records>'

output = []
i = 0
it = sickle.ListRecords(metadataPrefix='marc21', set='FULLMARC')
for record in it:
    tree = etree.ElementTree(record.xml)
    recs = tree.xpath('/oai:record[*]/oai:metadata/marc21:record', namespaces=namespaces)
    for rec in recs:
        core = etree.tostring(rec, encoding='utf8', method='xml').decode("utf-8")
        output.append(core.replace("<?xml version='1.0' encoding='utf8'?>\n", ''))
    if len(output) >= 1000:
        file_name = "%06d.xml" % (i)
        print(file_name)
        with io.open(file_name, 'w', encoding='utf8') as f:
            f.write(header)
            f.write("\n".join(output) + "\n")
            f.write(footer)
        output = []
        i = i + 1
