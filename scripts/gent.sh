#!/usr/bin/env bash

. /setdir.sh
NAME=gent
PREFIX=$DIR/_reports/${NAME}
TYPE_PARAMS="--marcVersion GENT"
MARC_DIR=${DIR}/gent/marc
MASK=*.mrc

#./validator $PARAMS --marcVersion GENT $DIR/gent/marc/*.mrc 2> ${PREFIX}.log

echo "Logging to ${PREFIX}.log"
. ./common-script

echo "DONE"
exit 0
