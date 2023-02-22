import io
import os
import sys
import re
from lxml import etree
from sickle import Sickle

if len(sys.argv) != 3:
    print('Please give an input file and an output directory name')
    exit()
in_file = sys.argv[1]
out_directory = sys.argv[2]
if not os.path.exists(out_directory):
    os.makedirs(out_directory)

out_file = re.sub(r'.*/', out_directory + '/', in_file)
print(out_file)

namespaces = {
    'oai': 'http://www.openarchives.org/OAI/2.0/',
    'marc21': 'http://www.loc.gov/MARC21/slim'
}
sickle = Sickle('http://services.kb.nl/mdo/oai', max_retries=4)
header = '<?xml version="1.0" encoding="utf8"?>' + "\n" + '<records>' + "\n" 
footer = '</records>'

def write_output(output, i, directory):
    """Writes MARC records to file""" 
    file_name = "%s/%06d.xml" % (directory, i)
    print(file_name)
    with io.open(file_name, 'w', encoding='utf8') as f:
        # f.write(header)
        f.write("\n".join(output) + "\n")
        # f.write(footer)

def get_record(id):
    # 'GGC:AC:357539168', ignore_deleted=True, picaplus
    record = sickle.GetRecord(metadataPrefix='picaplus', identifier=id) 
    tree = etree.ElementTree(record.xml)
    records = tree.xpath('oai:metadata/oai:record', namespaces=namespaces)
    xml = etree.tostring(records[0], encoding='utf8', method='xml').decode("utf-8")
    xml = xml.replace(' xmlns="http://www.openarchives.org/OAI/2.0/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"', '')
    return xml

with io.open(in_file) as infile:
    with io.open(out_file, 'w', encoding='utf8') as outfile:
        for id in infile:
            id = id.strip()
            outfile.write(get_record(id) + "\n")
