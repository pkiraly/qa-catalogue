#!/usr/bin/env bash
# Biblioteca Nationala a Romaniei
# https://www.bibnat.ro/

. ./setdir.sh
NAME=bnr
# TYPE_PARAMS="--marcVersion GENT"
TYPE_PARAMS=" --emptyLargeCollectors"
MARC_DIR=${BASE_INPUT_DIR}/bnr
MASK=bnr.*.mrc

. ./common-script
