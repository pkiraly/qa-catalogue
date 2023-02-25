#!/usr/bin/env bash
# Knihoveda

. ./setdir.sh
NAME=knihoveda
MARC_DIR=${BASE_INPUT_DIR}/knihoveda/
TYPE_PARAMS="--marcxml --emptyLargeCollectors"
MASK=K?.xml

. ./common-script

if [[ "$1" != "help" ]]; then
  echo "DONE"
fi

exit 0
