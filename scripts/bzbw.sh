#!/usr/bin/env bash

. /setdir.sh
NAME=bzbw
PREFIX=$DIR/_reports/${NAME}
MARC_DIR=${DIR}/bzbw/v2
MASK=*.mrc

# ./validator $PARAMS $DIR/bzbw/v2/od-full_bsz-tit_180319_*.mrc 2> ${PREFIX}.log

echo "Logging to ${PREFIX}.log"
. ./common-script

echo "DONE"
exit 0
