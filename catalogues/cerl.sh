#!/usr/bin/env bash
# The Heritage of the Printed Book Database
# https://www.cerl.org/resources/hpb/main/

. ./setdir.sh

NAME=cerl
MARC_DIR=${BASE_INPUT_DIR}/cerl/20150803
TYPE_PARAMS="--emptyLargeCollectors"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MASK=*.gz

. ./common-script
