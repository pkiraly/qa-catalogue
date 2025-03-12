#!/usr/bin/env bash
# Kansallis Kirjasto/National Biblioteket (The National Library of Finnland, Fennica catalogue)
# https://www.kansalliskirjasto.fi/en

. ./setdir.sh

NAME=nfi
TYPE_PARAMS="--marcVersion FENNICA --fixAlephseq --marcxml --emptyLargeCollectors"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MASK=fennica.mrcx

. ./common-script
