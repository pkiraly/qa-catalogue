# QA Catalogue Changelog

## v0.5

### New features

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