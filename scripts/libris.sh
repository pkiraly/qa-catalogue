#!/usr/bin/env bash

. ./setdir.sh
NAME=libris
# MARC_DIR=${BASE_INPUT_DIR}/loc/2019
TYPE_PARAMS="--marcxml"
MARC_DIR=/home/kiru/git/libris.kb.se
MASK=sw-?.xml

. ./common-script

echo "DONE"
exit 0
