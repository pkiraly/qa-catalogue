#!/usr/bin/env bash
# National Library of Israel
# https://www.nli.org.il/en

. ./setdir.sh

NAME=nli
TYPE_PARAMS="--alephseq --alephseqLineType WITHOUT_L --emptyLargeCollectors --indexWithTokenizedField"
MASK=xxqatest?.txt.gz

. ./common-script
