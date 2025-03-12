#!/usr/bin/env bash

. ./setdir.sh

NAME=oxfordmedicine
TYPE_PARAMS=""
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MASK=oxmed*.mrc

. ./common-script
