#!/usr/bin/env bash

. ./setdir.sh

NAME=harvard
MARC_DIR=${BASE_INPUT_DIR}/harvard/20220215
TYPE_PARAMS="--marcxml --emptyLargeCollectors"
MASK=20220215_*.xml.gz

. ./common-script
