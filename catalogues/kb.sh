#!/usr/bin/env bash

. ./setdir.sh
NAME=kb
MARC_DIR=${BASE_INPUT_DIR}/kb/2023-02-24
TYPE_PARAMS="--marcxml --emptyLargeCollectors --indexWithTokenizedField"
MASK=kb-marc*.xml.gz

. ./common-script

if [[ "$1" != "help" ]]; then
  echo "DONE"
fi

exit 0
