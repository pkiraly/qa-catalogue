#!/usr/bin/env bash
# Deutsche Digitale Bibliothek
# https://www.deutsche-digitale-bibliothek.de/

. ./setdir.sh

# TYPE_PARAMS='--ignorableFields A02,AQN,BGT,BUF,CFI,CNF,DGM,DRT,EST,EXP,FFP,FIN,LAS,LCS,LDO,LEO,LET,MIS,MNI,MPX,NEG,NID,OBJ,OHC,ONS,ONX,PLR,RSC,SRC,SSD,TOC,UNO,VIT,WII --ignorableRecords STA$a=SUPPRESSED'
TYPE_PARAMS='--emptyLargeCollectors --marcxml'
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
NAME=ddb
MASK=all.xml.gz

. ./common-script
