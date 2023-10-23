# QA Catalogue Changelog

## v0.8.0

- [\#326](https://github.com/pkiraly/metadata-qa-marc/issues/326) Use GETOPT for the command line scripts
- [\#104](https://github.com/pkiraly/qa-catalogue/issues/104) Implementing MARC Update No. 32, June 2021

## v0.5.0

### New features

- [\#94](https://github.com/pkiraly/metadata-qa-marc/issues/94) Change to Java 11
- [\#89](https://github.com/pkiraly/metadata-qa-marc/issues/89)
  Check definitins against [MARC updates](https://www.loc.gov/marc/status.html)
  - [Update No. 24 (May 2017)](https://www.loc.gov/marc/up24bibliographic/bdapndxg.html)
  - [Update No. 25 (December 2017)](https://www.loc.gov/marc/up25bibliographic/bdapndxg.html)
  - [Update No. 26 (April 2018)](https://www.loc.gov/marc/up26bibliographic/bdapndxg.html)
  - [Update No. 27 (November 2018)](https://www.loc.gov/marc/up27bibliographic/bdapndxg.html)
  - [Update No. 28 (May 2019)](https://www.loc.gov/marc/up28bibliographic/bdapndxg.html)
  - [Update No. 29 (November 2019)](https://www.loc.gov/marc/up29bibliographic/bdapndxg.html)
  - [Update No. 30 (May 2020)](https://www.loc.gov/marc/up30bibliographic/bdapndxg.html)
  - [Update No. 31 (December 2020)](https://www.loc.gov/marc/bibliographic/bdapndxg.html)
- Catalogue versions
  - [\#124](https://github.com/pkiraly/metadata-qa-marc/issues/124) KBR MARC version
  - [\#99](https://github.com/pkiraly/metadata-qa-marc/issues/99) B3Kat version
  - [\#95](https://github.com/pkiraly/metadata-qa-marc/issues/95) MARC21NO version
  - [\#75](https://github.com/pkiraly/metadata-qa-marc/issues/75) British Library version
- Avram schema files related developments
  - [\#127](https://github.com/pkiraly/metadata-qa-marc/issues/127) Include version specific subfields to the JSON 
  schema representation and completeness
  - [\#109](https://github.com/pkiraly/metadata-qa-marc/issues/109) Add solr fields of control fields into the JSON 
   schema
  - [\#118](https://github.com/pkiraly/metadata-qa-marc/issues/118) Provide alternative Avram versions, and make them
  transparently available
- Completeness
  - [\#106](https://github.com/pkiraly/metadata-qa-marc/issues/106) include indicators
  - [\#86](https://github.com/pkiraly/metadata-qa-marc/issues/86) display indicators
  - [\#83](https://github.com/pkiraly/metadata-qa-marc/issues/83) include control fields
  - [\#81](https://github.com/pkiraly/metadata-qa-marc/issues/81) use IDs for package info
  - [\#80](https://github.com/pkiraly/metadata-qa-marc/issues/80) count package by doctype
  - [\#79](https://github.com/pkiraly/metadata-qa-marc/issues/79) create a reusable FieldInfo class for completeness
  - [\#78](https://github.com/pkiraly/metadata-qa-marc/issues/78) Add package other/unknwon to the package statistics
- General parameters
  - [\#105](https://github.com/pkiraly/metadata-qa-marc/issues/105) Add defaultEncoding parameter
  - [\#101](https://github.com/pkiraly/metadata-qa-marc/issues/101) Read gzipped MARC files
  - [\#100](https://github.com/pkiraly/metadata-qa-marc/issues/100) Creating a replacement for # in control field from
    Alma output
- [\#126](https://github.com/pkiraly/metadata-qa-marc/issues/126) Skip records without errors from issue-details.csv
- [\#125](https://github.com/pkiraly/metadata-qa-marc/issues/125) ignorableFields should not be mentioned in 
  undefined fields
- [\#116](https://github.com/pkiraly/metadata-qa-marc/issues/116) Create an INSTALL.md file with installation 
  instructions
- [\#113](https://github.com/pkiraly/metadata-qa-marc/issues/113) Reading MARCMaker format
- [\#107](https://github.com/pkiraly/metadata-qa-marc/issues/107) Reorganize scripts directory. Right now there is a
  `catalogue` directory for the catalogue specific configuration files, and a `scripts` directory with some 
  subdirectories for the analyses

## v0.4

### Added

- British Library tags: 039, 091, 509, 539, 590, 591, 592, 594, 595, 596, 597, 598, 599,
  690, 692, 852$a (code list), 859, 909, 916, 917, 945, 950, 954, 955, 957, 959, 960,
  961, 962, 963, 964, 966, 968, 970, 975, 976, 979, 980, 985, 990, 992, 996, 997,
  A02, AQN, BGT, BUF, CFI, CNF, DGM, DRT, EST, EXP, FFP, FIN, LAS, LCS, LDO, LEO, LET,
  MIS, MNI, MPX, NEG, NID, OBJ, OHC, ONS, ONX, PLR, RSC, SRC, SSD, TOC, UNO, VIT, WII
- new general parameters
  - ignorableFields
  - ignorableRecords
- set a field as DataFields of the record even if it doesn't have definition
- new validators: DateValidator, RegexValidator, RangeValidator