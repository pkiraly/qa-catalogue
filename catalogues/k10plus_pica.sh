#!/usr/bin/env bash

. ./setdir.sh
NAME=k10plus_pica
MARC_DIR=${BASE_INPUT_DIR}/k10plus_pica
TYPE_PARAMS="--schemaType PICA --marcFormat PICA_PLAIN --emptyLargeCollectors"
MASK=sample.pica

. ./common-script

if [[ "$1" != "help" ]]; then
  echo "DONE"
fi

exit 0
