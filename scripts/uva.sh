#!/usr/bin/env bash

. ./setdir.sh
NAME=uva
MARC_DIR=${BASE_INPUT_DIR}/uva/2021-07-07
TYPE_PARAMS="--marcxml"
MASK=*.xml

. ./common-script

if [[ "$1" != "help" ]]; then
  echo "DONE"
fi

exit 0
