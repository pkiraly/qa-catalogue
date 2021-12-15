#!/usr/bin/env bash

. ./setdir.sh
NAME=K10plus
MARC_DIR=${BASE_INPUT_DIR}/K10plus
TYPE_PARAMS="--marcxml --emptyLargeCollectors --fixAlma"
MASK=od-full_bsz-tit_0??.xml.gz

. ./common-script

if [[ "$1" != "help" ]]; then
  echo "DONE"
fi

exit 0
