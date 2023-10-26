#!/usr/bin/env bash

. ./setdir.sh

NAME=loc
MARC_DIR=${BASE_INPUT_DIR}/loc/2019
TYPE_PARAMS="--emptyLargeCollectors --indexWithTokenizedField"
MASK=*.utf8.gz

. ./common-script
