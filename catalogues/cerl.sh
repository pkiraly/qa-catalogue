#!/usr/bin/env bash
# The Heritage of the Printed Book Database
# https://www.cerl.org/resources/hpb/main/

. ./setdir.sh

NAME=cerl
MARC_DIR=${BASE_INPUT_DIR}/cerl/20150803
TYPE_PARAMS="--emptyLargeCollectors --indexWithTokenizedField"
MASK=*.gz

. ./common-script
