#!/usr/bin/env bash

. /setdir.sh
NAME=harvard
PREFIX=$DIR/_reports/${NAME}
MARC_DIR=${DIR}/harvard
MASK=*.mrc

#./validator $PARAMS $DIR/harvard/*.mrc 2> ${PREFIX}.log

echo "Logging to ${PREFIX}.log"
. ./common-script

echo "DONE"
exit 0
