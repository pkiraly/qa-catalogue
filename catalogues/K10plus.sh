#!/usr/bin/env bash
# K10plus-Verbundkatalog (MARC records)
# https://opac.k10plus.de

. ./setdir.sh

NAME=K10plus
TYPE_PARAMS="--marcxml --emptyLargeCollectors --fixAlma"
MASK=od-full_bsz-tit_0??.xml.gz

. ./common-script
