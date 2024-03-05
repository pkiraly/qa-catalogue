#!/usr/bin/env bash
# Národní knihovna České republiky - National Library of the Czech Republic
# https://nkp.cz/

. ./setdir.sh

NAME=nkp
MARC_DIR=${BASE_INPUT_DIR}/nkp.cz/nkp
TYPE_PARAMS="--marcxml --emptyLargeCollectors --marcVersion NKCR"
MASK=cnb.xml.gz

. ./common-script
