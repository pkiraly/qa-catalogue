#!/usr/bin/env bash

. ./setdir.sh
# TYPE_PARAMS='--ignorableFields A02,AQN,BGT,BUF,CFI,CNF,DGM,DRT,EST,EXP,FFP,FIN,LAS,LCS,LDO,LEO,LET,MIS,MNI,MPX,NEG,NID,OBJ,OHC,ONS,ONX,PLR,RSC,SRC,SSD,TOC,UNO,VIT,WII --ignorableRecords STA$a=SUPPRESSED'
TYPE_PARAMS='--marcVersion BL --ignorableRecords STA$a=SUPPRESSED --emptyLargeCollectors'
NAME=bl
MARC_DIR=${BASE_INPUT_DIR}/bl
MASK=full*.lex


. ./common-script

echo "DONE"
exit 0
