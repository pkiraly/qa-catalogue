#!/usr/bin/env bash

. /setdir.sh
NAME=bayern
PREFIX=$DIR/_reports/${NAME}
MARC_DIR=${DIR}/bibliotheksverbundes-bayern
MASK=*.mrc

# ./validator $PARAMS $DIR/bibliotheksverbundes-bayern/*.mrc 2> ${PREFIX}.log

echo "Logging to ${PREFIX}.log"
. ./common-script

echo "DONE"
exit 0
