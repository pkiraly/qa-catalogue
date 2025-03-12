#!/usr/bin/env bash
# Österreichische Nationalbibliothek (Austrian National Library)
# https://www.onb.ac.at/

. ./setdir.sh

NAME=onb
TYPE_PARAMS=" --emptyLargeCollectors --fixAlma"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MASK=onb*.mrc.gz

. ./common-script
