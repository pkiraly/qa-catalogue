#!/usr/bin/env bash

. ./setdir.sh

NAME=hbz-update
TYPE=xml
if [[ $TYPE == "marc" ]]; then
  echo "marc"
  TYPE_PARAMS="--marcVersion HBZ --fixAlma"
  MARC_DIR=${BASE_INPUT_DIR}/${NAME}/marc
  MASK=*.mrc
elif [[ $TYPE == "xml" ]]; then
  echo "xml"
  TYPE_PARAMS="--marcVersion HBZ --marcxml --fixAlma --ignorableRecords DEL$a=Y  --ignorableFields 964,940,941,942,944,945,946,947,948,949,950,951,952,955,956,957,958,959,966,967,970,971,972,973,974,975,976,977,978,978,979"
  MARC_DIR=${BASE_INPUT_DIR}/${NAME}/marc
  MASK=*.gz
else
  echo "else: ${TYPE}"
fi

. ./common-script
