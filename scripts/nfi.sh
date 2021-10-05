#!/usr/bin/env bash

. ./setdir.sh
NAME=nfi
TYPE_PARAMS="--marcVersion FENNICA --fixAlephseq --marcxml"
MARC_DIR=${BASE_INPUT_DIR}/nfi
MASK=fennica.mrcx

. ./common-script

echo "DONE"
exit 0
