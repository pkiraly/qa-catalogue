#!/usr/bin/env bash

. ./setdir.sh

NAME=clb
MARC_DIR=${BASE_INPUT_DIR}/clb
TYPE_PARAMS="--marcxml --emptyLargeCollectors --marcVersion NKCR"
MASK=ucloall.xml.gz

. ./common-script
