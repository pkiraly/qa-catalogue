#!/usr/bin/env bash

. /setdir.sh
NAME=rism
PREFIX=$DIR/_reports/${NAME}
MARC_DIR=${DIR}/rism
MASK=*.mrc

#./validator $PARAMS $DIR/rism/*.mrc 2> ${PREFIX}.log

echo "Logging to ${PREFIX}.log"
. ./common-script

echo "DONE"
exit 0
