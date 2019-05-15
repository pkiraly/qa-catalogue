#!/usr/bin/env bash

. /setdir.sh
NAME=cerl
PREFIX=$DIR/_reports/${NAME}
MARC_DIR=${DIR}/cerl/20150803
MASK=*.mrc

# ./validator $PARAMS $DIR/cerl/20150803/*.mrc 2> ${PREFIX}.log

echo "Logging to ${PREFIX}.log"
. ./common-script

echo "DONE"
exit 0
