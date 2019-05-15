#!/usr/bin/env bash

. /setdir.sh
NAME=dnb
PREFIX=$DIR/_reports/${NAME}
TYPE_PARAMS="--marcVersion DNB"
MARC_DIR=${DIR}/dnb/marc
MASK=*.mrc

# ./validator $PARAMS --marcVersion DNB $DIR/dnb/marc/*.mrc 2> ${PREFIX}.log

echo "Logging to ${PREFIX}.log"
. ./common-script

echo "DONE"
exit 0
