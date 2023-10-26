#!/usr/bin/env bash

. ./setdir.sh

NAME=libris
MARC_DIR=${BASE_INPUT_DIR}/libris
TYPE_PARAMS="--emptyLargeCollectors --marcxml --indexWithTokenizedField"
MASK=sw-?.xml.gz

. ./common-script
