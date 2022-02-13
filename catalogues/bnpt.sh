#!/usr/bin/env bash

. ./setdir.sh
NAME=bnpt
# TYPE_PARAMS="--marcVersion GENT"
TYPE_PARAMS=" --marcxml --emptyLargeCollectors"
MARC_DIR=${BASE_INPUT_DIR}/bnpt
MASK=bibliographics_*.xml

. ./common-script

echo "DONE"
exit 0
