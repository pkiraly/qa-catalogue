#!/usr/bin/env bash

# Biblioteca Nationala a Romaniei

. ./setdir.sh
NAME=bnr
# TYPE_PARAMS="--marcVersion GENT"
TYPE_PARAMS=" --emptyLargeCollectors"
MARC_DIR=${BASE_INPUT_DIR}/bnr
MASK=bnr.*.mrc

. ./common-script

echo "DONE"
exit 0
