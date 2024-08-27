#!/usr/bin/env bash

. ./setdir.sh

NAME=hbz
TYPE=xml
if [[ $TYPE == "marc" ]]; then
  echo "marc"
  TYPE_PARAMS="--marcVersion MARC21 --fixAlma"
  MARC_DIR=${BASE_INPUT_DIR}/${NAME}/marc
  MASK=*.mrc
elif [[ $TYPE == "xml" ]]; then
  echo "xml"
  TYPE_PARAMS="--marcVersion DNB,HBZ --marcxml --fixAlma"
  MARC_DIR=${BASE_INPUT_DIR}/${NAME}/marc
  MASK=*.gz
else
  echo "else: ${TYPE}"
fi

. ./common-script
