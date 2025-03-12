#!/usr/bin/env bash
# Magyar Országos Közös Katalógus
# http://mokka.hu/

. ./setdir.sh

NAME=mokka
TYPE_PARAMS="--marcxml --emptyLargeCollectors"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
# TYPE_PARAMS="--marcVersion SZTE"
MASK=all.xml.gz

. ./common-script
