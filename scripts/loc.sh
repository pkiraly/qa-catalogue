#!/usr/bin/env bash

. /setdir.sh
NAME=loc
PREFIX=$DIR/_reports/${NAME}
MARC_DIR=${DIR}/loc/marc
MASK=*.mrc

#./validator $PARAMS $DIR/loc/marc/*.mrc 2> ${PREFIX}.log

echo "Logging to ${PREFIX}.log"
. ./common-script

echo "DONE"
exit 0
