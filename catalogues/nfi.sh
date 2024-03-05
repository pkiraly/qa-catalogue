#!/usr/bin/env bash
# Kansallis Kirjasto/National Biblioteket (The National Library of Finnland, Fennica catalogue)
# https://www.kansalliskirjasto.fi/en

. ./setdir.sh

NAME=nfi
TYPE_PARAMS="--marcVersion FENNICA --fixAlephseq --marcxml --emptyLargeCollectors"
MARC_DIR=${BASE_INPUT_DIR}/nfi
MASK=fennica.mrcx

. ./common-script
