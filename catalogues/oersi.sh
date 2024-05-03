#!/usr/bin/env bash

. ./setdir.sh

NAME=oersi
TYPE=xml
if [[ $TYPE == "marc" ]]; then
  echo "marc"
  TYPE_PARAMS="--marcVersion DNB"
  MARC_DIR=${BASE_INPUT_DIR}/oersi/marc
  MASK=*.mrc
elif [[ $TYPE == "xml" ]]; then
  echo "xml"
  # TYPE_PARAMS="--marcVersion DNB --marcxml"
  TYPE_PARAMS="--marcVersion ZB --marcxml --fixAlma --emptyLargeCollectors"
  MARC_DIR=${BASE_INPUT_DIR}/oersi/marc
  MASK=*.gz
else
  echo "else: ${TYPE}"
fi

. ./common-script
