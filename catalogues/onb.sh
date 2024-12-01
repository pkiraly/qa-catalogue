#!/usr/bin/env bash
# Österreichische Nationalbibliothek (Austrian National Library)
# https://www.onb.ac.at/

. ./setdir.sh

NAME=onb
TYPE_PARAMS=" --emptyLargeCollectors --fixAlma --indexWithTokenizedField"
MASK=onb*.mrc.gz

. ./common-script
