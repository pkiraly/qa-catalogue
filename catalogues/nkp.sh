#!/usr/bin/env bash

. ./setdir.sh
NAME=nkp
MARC_DIR=${BASE_INPUT_DIR}/nkp
TYPE_PARAMS="--marcxml --emptyLargeCollectors --marcVersion NKCR"
MASK=cnb.xml.gz

. ./common-script

echo "DONE"
exit 0
