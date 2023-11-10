#!/usr/bin/env bash

. ./setdir.sh

NAME=yale
MARC_DIR=${BASE_INPUT_DIR}/yale/2023-11-05
TYPE_PARAMS="--emptyLargeCollectors --indexWithTokenizedField"
MASK=bib_*.mrc

. ./common-script
