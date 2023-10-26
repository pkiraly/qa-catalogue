#!/usr/bin/env bash

. ./setdir.sh

NAME=onb
MARC_DIR=${BASE_INPUT_DIR}/onb
TYPE_PARAMS=" --emptyLargeCollectors --fixAlma --indexWithTokenizedField"
MASK=onb*.mrc.gz

. ./common-script
