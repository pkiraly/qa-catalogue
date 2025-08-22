#!/usr/bin/env bash

. ./setdir.sh

NAME=nlm
TYPE_PARAMS="--emptyLargeCollectors"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MASK=catplusbase.202?

. ./common-script
