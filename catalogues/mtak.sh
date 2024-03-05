#!/usr/bin/env bash
# A Magyar Tudományos Akadémia Könyvtára
# https://mtak.hu/

. ./setdir.sh

NAME=mtak
TYPE_PARAMS="--marcxml --indexWithTokenizedField"
MARC_DIR=${BASE_INPUT_DIR}/mtak/2019-06-14
#MASK=*.mrc
MASK=*.marcxml.gz

. ./common-script
