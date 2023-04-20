#!/usr/bin/env bash

. ./setdir.sh

NAME=uva
MARC_DIR=${BASE_INPUT_DIR}/uva/2021-07-07
TYPE_PARAMS="--marcxml --marcVersion UVA --emptyLargeCollectors --fixAlma"
MASK=uva_*.xml.gz

. ./common-script
