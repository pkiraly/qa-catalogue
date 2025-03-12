#!/usr/bin/env bash
# Bibliothèque nationale de France
# https://www.bnf.fr

. ./setdir.sh

NAME=bnf
MARC_DIR=${BASE_INPUT_DIR}/bnf
TYPE_PARAMS="--emptyLargeCollectors --schemaType UNIMARC --defaultEncoding UTF8"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MASK=P174_*.UTF8

. ./common-script
