#!/usr/bin/env bash
# Magyar Elektronikus Könyvtár
# https://mek.oszk.hu/

. ./setdir.sh

NAME=mek
MARC_DIR=${BASE_INPUT_DIR}/mek
TYPE_PARAMS="--emptyLargeCollectors --defaultEncoding MARC8"
MASK=MEKmind.mrc

. ./common-script
