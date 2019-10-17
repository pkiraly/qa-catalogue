#!/usr/bin/env bash

. ./setdir.sh
NAME=bnpl
#TYPE_PARAMS="--marcVersion GENT"
MARC_DIR=${BASE_INPUT_DIR}/bnpl
MASK=*.marc

. ./common-script

echo "DONE"
exit 0
