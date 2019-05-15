#!/usr/bin/env bash

. /setdir.sh
NAME=stanford
PREFIX=${DIR}/_reports/${NAME}
MARC_DIR=${DIR}/stanford
MASK=*.marc

echo "Logging to ${PREFIX}.log"
. ./common-script

echo "DONE"
exit 0
