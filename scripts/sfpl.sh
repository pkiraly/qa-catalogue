#!/usr/bin/env bash

. /setdir.sh
NAME=sfpl
PREFIX=$DIR/_reports/${NAME}
MARC_DIR=${DIR}/san-francisco-public-library/marc
MASK=*.mrc

#./validator $PARAMS $DIR/san-francisco-public-library/marc/*.mrc 2> ${PREFIX}.log

echo "Logging to ${PREFIX}.log"
. ./common-script

echo "DONE"
exit 0
