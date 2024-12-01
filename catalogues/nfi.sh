#!/usr/bin/env bash
# Kansallis Kirjasto/National Biblioteket (The National Library of Finnland, Fennica catalogue)
# https://www.kansalliskirjasto.fi/en

. ./setdir.sh

NAME=nfi
TYPE_PARAMS="--marcVersion FENNICA --fixAlephseq --marcxml --emptyLargeCollectors"
MASK=fennica.mrcx

. ./common-script
