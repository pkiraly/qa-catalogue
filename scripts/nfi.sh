#!/usr/bin/env bash

. ./setdir.sh
NAME=nfi
TYPE_PARAMS="--marcVersion FENNICA --fixAlephseq"
MARC_DIR=${BASE_INPUT_DIR}/nationallibrary.fi
MASK=*.mrc

. ./common-script

echo "DONE"
exit 0
