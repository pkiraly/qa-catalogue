#!/usr/bin/env bash

. /setdir.sh
NAME=nfi
PREFIX=$DIR/_reports/${NAME}
TYPE_PARAMS="--marcVersion FENNICA"
MARC_DIR=${DIR}/nationallibrary.fi
MASK=*.mrc

#./validator $PARAMS --marcVersion FENNICA $DIR/nationallibrary.fi/*.mrc 2> ${PREFIX}.log

echo "Logging to ${PREFIX}.log"
. ./common-script

echo "DONE"
exit 0
