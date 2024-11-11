# MARC metadata schema in Avram compatible JSON

This directory contains the following versions of the MARC21 schema:

* `marc-schema.json` -- MARC21 bibliographic schema as defined by the Library of Congress
* `marc-schema-with-solr.json` -- with Solr field mapping
* `marc-schema-with-solr-and-extensions.json` -- with Solr field mapping and with locally defined fields of the following libraries: 

| code | library |
| ---- |---------|
| DNB | Deuthche Nationalbibliothek (Germany) |
| OCLC | OCLC (USA) |
| GENT | Gent University (Belgium) |
| SZTE | Szegedi Tudományegyetem (Hungary) |
| FENNICA | the Fennica catalog of Finnish National Library (Finland) |
| NKCR | National Library of the Czech Republic |
| BL | British Library (United Kingdom) |
| MARC21NO | the MARC21 profile for Norwegian public libraries (Norway) |
| UVA | University of Amsterdam Library (The Netherlands) |
| B3KAT | B3Kat union catalogue of Bibliotheksverbundes Bayern (BVB) and Kooperativen Bibliotheksverbundes Berlin-Brandenburg (KOBV) (Germany) |
| KBR | KBR, the national library of Belgium |
| ZB | Zentralbibliothek Zürich (Switzerland) |
| OGYK | Országygyűlési Könyvtár, Budapest (Hungary) |

To generate these files, run

```bash
./qa-catalogue export-schema-files
```

To validate the files, run

```bash
./avram-schemas/validate-schemas
```
