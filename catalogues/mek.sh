#!/usr/bin/env bash

. ./setdir.sh
NAME=mek
MARC_DIR=${BASE_INPUT_DIR}/mek
TYPE_PARAMS="--emptyLargeCollectors --defaultEncoding MARC8"
MASK=MEKmind.mrc

. ./common-script

if [[ "$1" != "help" ]]; then
  echo "DONE"
fi

exit 0
