#!/usr/bin/env bash

. ./setdir.sh
NAME=nls
MARC_DIR=${BASE_INPUT_DIR}/nls
TYPE_PARAMS="--marcxml --emptyLargeCollectors --indexWithTokenizedField"
MASK=NBS_v2_validated_marcxml.xml.gz

. ./common-script
