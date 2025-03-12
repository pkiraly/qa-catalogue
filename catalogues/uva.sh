#!/usr/bin/env bash
# Bibliotheek Universiteit van Amsterdam/Hogeschool van Amsterdam
# https://uba.uva.nl/home

. ./setdir.sh

NAME=uva
MARC_DIR=${BASE_INPUT_DIR}/uva/2021-07-07
TYPE_PARAMS="--marcxml --marcVersion UVA --emptyLargeCollectors --fixAlma"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MASK=uva_*.xml.gz

. ./common-script
