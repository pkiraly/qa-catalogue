#!/usr/bin/env bash
# Verbundzentrale des Gemeinsamen Bibliotheksverbundes
# http://www.gbv.de/

. ./setdir.sh

NAME=gbv
TYPE_PARAMS="--emptyLargeCollectors"
MARC_DIR=${BASE_INPUT_DIR}/gbv/2019-10-01/marc
MASK=*.mrc

. ./common-script
