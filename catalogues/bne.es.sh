#!/usr/bin/env bash
# National Library of Spain
# https://bnelab.bne.es/en/dataset/bibliographic-catalogue/

. ./setdir.sh

NAME=bne
MARC_DIR=${BASE_INPUT_DIR}/bne.es
TYPE_PARAMS="--emptyLargeCollectors"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MASK=*.mrc.gz

. ./common-script
