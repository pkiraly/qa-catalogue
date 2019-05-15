#!/usr/bin/env bash

. /setdir.sh
NAME=columbia
PREFIX=$DIR/_reports/${NAME}
MARC_DIR=${DIR}/columbia
MASK=*.mrc

# ./validator $PARAMS $DIR/columbia/*.mrc 2> ${PREFIX}.log

echo "Logging to ${PREFIX}.log"
. ./common-script

echo "DONE"
exit 0
