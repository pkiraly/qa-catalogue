#!/usr/bin/env bash
# Deutsche Nationalbibliothek
# https://www.dnb.de/

. ./setdir.sh

NAME=dnb
TYPE=xml
if [[ $TYPE == "marc" ]]; then
  echo "marc"
  TYPE_PARAMS="--marcVersion DNB"
  MARC_DIR=${BASE_INPUT_DIR}/dnb/marc
  MASK=*.mrc.gz
elif [[ $TYPE == "xml" ]]; then
  echo "xml"
  TYPE_PARAMS="--marcVersion DNB --marcxml"
  MARC_DIR=${BASE_INPUT_DIR}/dnb
  MASK=*.mrc.xml.gz
else
  echo "else: ${TYPE}"
fi

. ./common-script
