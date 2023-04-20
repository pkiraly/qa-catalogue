#!/usr/bin/env bash
# Zentralbibliothek Zürich

. ./setdir.sh

NAME=zb
MARC_DIR=${BASE_INPUT_DIR}/zb/
TYPE_PARAMS="--marcxml --fixAlma --emptyLargeCollectors"
# MASK=BIB*.xml
# MASK=lines.xml
MASK=datadump-zb3.xml

. ./common-script
