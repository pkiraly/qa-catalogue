#!/usr/bin/env bash
# Verbundzentrale des Gemeinsamen Bibliotheksverbundes
# http://www.gbv.de/

. ./setdir.sh

NAME=gbv
TYPE_PARAMS="--emptyLargeCollectors"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MARC_DIR=${BASE_INPUT_DIR}/gbv/2019-10-01/marc
MASK=*.mrc

. ./common-script
