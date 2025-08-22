#!/usr/bin/env bash
# National Library of Israel
# https://www.nli.org.il/en

. ./setdir.sh

NAME=nli
TYPE_PARAMS="--alephseq --alephseqLineType WITHOUT_L --emptyLargeCollectors"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MASK=xxqatest?.txt.gz

. ./common-script
