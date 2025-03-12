#!/usr/bin/env bash
# Library of Congress
# https://catalog.loc.gov/

. ./setdir.sh

NAME=loc
MARC_DIR=${BASE_INPUT_DIR}/loc/2019
TYPE_PARAMS="--emptyLargeCollectors"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MASK=*.utf8.gz

. ./common-script
