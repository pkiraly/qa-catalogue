#!/usr/bin/env bash
# Zentralbibliothek Zürich

. ./setdir.sh

NAME=zb
MARC_DIR=${BASE_INPUT_DIR}/zb/
TYPE_PARAMS="--marcxml --fixAlma --emptyLargeCollectors"
MASK=${MASK:=datadump-zb3.xml.gz}

. ./common-script
