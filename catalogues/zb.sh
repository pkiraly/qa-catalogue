#!/usr/bin/env bash

. ./setdir.sh
NAME=zb
MARC_DIR=${BASE_INPUT_DIR}/zb/
TYPE_PARAMS="--marcxml --fixAlma --emptyLargeCollectors"
MASK=BIB*.xml

. ./common-script

if [[ "$1" != "help" ]]; then
  echo "DONE"
fi

exit 0
