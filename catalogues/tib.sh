#!/usr/bin/env bash

. ./setdir.sh

NAME=tib
MARC_DIR=${BASE_INPUT_DIR}/tib/mrc
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MASK=*.mrc

. ./common-script
