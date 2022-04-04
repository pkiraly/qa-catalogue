#!/usr/bin/env bash

. ./setdir.sh
NAME=lnb
MARC_DIR=${BASE_INPUT_DIR}/lnb.lv
TYPE_PARAMS="--marcxml --emptyLargeCollectors"
MASK=nat_bibliography-2017_2020-marc.xml.gz

. ./common-script

if [[ "$1" != "help" ]]; then
  echo "DONE"
fi

exit 0
