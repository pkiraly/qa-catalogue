#!/usr/bin/env bash

. /setdir.sh
NAME=tib
PREFIX=$DIR/_reports/${NAME}
MARC_DIR=${DIR}/tib/mrc
MASK=*.mrc

# ./validator $PARAMS $DIR/tib/mrc/*.mrc 2> ${PREFIX}.log

echo "Logging to ${PREFIX}.log"
. ./common-script

echo "DONE"
exit 0
