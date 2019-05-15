#!/usr/bin/env bash

. /setdir.sh
NAME=szte
PREFIX=$DIR/_reports/${NAME}
TYPE_PARAMS="--marcVersion SZTE"
MARC_DIR=${DIR}/szte/marc/2019-04-25
MASK=*.mrc

# ./validator $PARAMS $DIR/szte/marc/*.mrc 2> ${PREFIX}.log

echo "Logging to ${PREFIX}.log"
. ./common-script

echo "DONE"
exit 0
