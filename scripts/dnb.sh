#!/usr/bin/env bash

. ./setdir.sh
NAME=dnb
TYPE_PARAMS="--marcVersion DNB"
MARC_DIR=${BASE_INPUT_DIR}/dnb/marc
MASK=*.mrc

. ./common-script

echo "DONE"
exit 0
