#!/usr/bin/env bash
# Česká literární bibliografie
# https://clb.ucl.cas.cz/

. ./setdir.sh

NAME=clb
TYPE_PARAMS="--marcxml --emptyLargeCollectors --marcVersion NKCR"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MASK=ucloall.xml.gz

. ./common-script
