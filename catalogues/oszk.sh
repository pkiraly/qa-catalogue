#!/usr/bin/env bash
# Országos Széchényi Könyvtár (Hungarian National Library)
# https://www.oszk.hu/

. ./setdir.sh
NAME=oszk
MARC_DIR=${BASE_INPUT_DIR}/oszk
TYPE_PARAMS="--emptyLargeCollectors --defaultEncoding UTF8 --indexWithTokenizedField"
MASK=OSZK_*.mrc.gz

. ./common-script

echo "DONE"
exit 0
