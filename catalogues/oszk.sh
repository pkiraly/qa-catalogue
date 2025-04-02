#!/usr/bin/env bash
# Országos Széchényi Könyvtár (Hungarian National Library)
# https://www.oszk.hu/

. ./setdir.sh
NAME=oszk
TYPE_PARAMS="--emptyLargeCollectors --defaultEncoding UTF8 --marcVersion HUNMARC"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MASK=OSZK_*.mrc.gz

. ./common-script
