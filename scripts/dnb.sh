#!/usr/bin/env bash

. ./setdir.sh
NAME=dnb
TYPE=xml
if [[ $TYPE == "marc" ]]; then
  echo "marc"
  TYPE_PARAMS="--marcVersion DNB"
  MARC_DIR=${BASE_INPUT_DIR}/dnb/marc
  MASK=*.mrc
elif [[ $TYPE == "xml" ]]; then
  echo "xml"
  TYPE_PARAMS="--marcVersion DNB --marcxml"
  MARC_DIR=${BASE_INPUT_DIR}/dnb/xml
  MASK=*.mrc.xml
else
  echo "else: ${TYPE}"
fi

. ./common-script

echo "DONE"
exit 0
