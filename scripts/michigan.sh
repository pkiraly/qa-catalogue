#!/usr/bin/env bash

. /setdir.sh
NAME=michigan
PREFIX=$DIR/_reports/${NAME}
MARC_DIR=${DIR}/michigan
MASK=*.marc

#./validator $PARAMS $DIR/michigan/*.marc 2> ${PREFIX}.log

echo "Logging to ${PREFIX}.log"
. ./common-script

echo "DONE"
exit 0
