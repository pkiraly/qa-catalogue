#!/usr/bin/env bash

. ./setdir.sh

NAME=bayern
MARC_DIR=${BASE_INPUT_DIR}/bvb/marc
TYPE_PARAMS="--marcxml --marcVersion B3KAT --emptyLargeCollectors"
MASK=b3kat*.xml.gz

. ./common-script
