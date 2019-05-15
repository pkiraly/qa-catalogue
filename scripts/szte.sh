#!/usr/bin/env bash

. ./setdir.sh
NAME=szte
TYPE_PARAMS="--marcVersion SZTE"
MARC_DIR=${BASE_INPUT_DIR}/szte/marc/2019-04-25
MASK=*.mrc

. ./common-script

echo "DONE"
exit 0
