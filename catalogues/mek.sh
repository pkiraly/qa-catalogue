#!/usr/bin/env bash
# Magyar Elektronikus Könyvtár
# https://mek.oszk.hu/

. ./setdir.sh

NAME=mek
TYPE_PARAMS="--emptyLargeCollectors --defaultEncoding MARC8"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MASK=MEKmind.mrc

. ./common-script
