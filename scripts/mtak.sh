#!/usr/bin/env bash

. ./setdir.sh
NAME=mtak
TYPE_PARAMS="--marcxml"
MARC_DIR=${BASE_INPUT_DIR}/mtak/2019-06-14
#MASK=*.mrc
MASK=*.marcxml

. ./common-script

echo "DONE"
exit 0
