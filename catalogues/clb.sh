#!/usr/bin/env bash
# Česká literární bibliografie
# https://clb.ucl.cas.cz/

. ./setdir.sh

NAME=clb
MARC_DIR=${BASE_INPUT_DIR}/clb
TYPE_PARAMS="--marcxml --emptyLargeCollectors --marcVersion NKCR"
MASK=ucloall.xml.gz

. ./common-script
