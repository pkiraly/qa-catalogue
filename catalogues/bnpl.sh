#!/usr/bin/env bash

. ./setdir.sh

NAME=bnpl
# TYPE_PARAMS="--marcVersion GENT"
TYPE_PARAMS=" --emptyLargeCollectors --indexWithTokenizedField"
MARC_DIR=${BASE_INPUT_DIR}/bnpl
MASK=bibs-all.marc.gz

. ./common-script
