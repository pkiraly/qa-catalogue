#!/usr/bin/env bash

. ./setdir.sh

NAME=test
TYPE=xml
if [[ $TYPE == "marc" ]]; then
  echo "marc"
  TYPE_PARAMS="--marcVersion DNB"
  MARC_DIR=${BASE_INPUT_DIR}/test/marc
  MASK=*.mrc
elif [[ $TYPE == "xml" ]]; then
  echo "xml"
  TYPE_PARAMS="--marcVersion DNB --marcxml"
  MARC_DIR=${BASE_INPUT_DIR}/test/marc
  MASK=*.xml
else
  echo "else: ${TYPE}"
fi

. ./common-script
