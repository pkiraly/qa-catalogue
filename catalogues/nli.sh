#!/usr/bin/env bash

. ./setdir.sh
NAME=nli
TYPE_PARAMS="--alephseq --alephseqLineType WITHOUT_L"
MARC_DIR=${BASE_INPUT_DIR}/nli
MASK=xxqatest?.txt.gz

. ./common-script

echo "DONE"
exit 0
