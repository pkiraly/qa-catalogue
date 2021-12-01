#!/usr/bin/env bash

. ./setdir.sh
NAME=kbr
MARC_DIR=${BASE_INPUT_DIR}/kbr
TYPE_PARAMS="--emptyLargeCollectors"
MASK=KBR_20210713_B_0?.mrc.gz

. ./common-script

if [[ "$1" != "help" ]]; then
  echo "DONE"
fi

exit 0
