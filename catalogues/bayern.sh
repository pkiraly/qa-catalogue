#!/usr/bin/env bash
# Verbundkatalog B3Kat des Bibliotheksverbundes Bayern (BVB) und des Kooperativen Bibliotheksverbundes Berlin-Brandenburg (KOBV)
# https://www.bib-bvb.de/

. ./setdir.sh

NAME=bayern
MARC_DIR=${BASE_INPUT_DIR}/bvb/marc
TYPE_PARAMS="--marcxml --marcVersion B3KAT --emptyLargeCollectors"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MASK=b3kat*.xml.gz

. ./common-script
