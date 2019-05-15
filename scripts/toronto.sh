#!/usr/bin/env bash

. /setdir.sh
NAME=toronto
PREFIX=$DIR/_reports/${NAME}
MARC_DIR=${DIR}/toronto
MASK=*.mrc

# ./validator $PARAMS $DIR/toronto/*.marc 2> ${PREFIX}.log

echo "Logging to ${PREFIX}.log"
. ./common-script

echo "DONE"
exit 0
