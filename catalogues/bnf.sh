#!/usr/bin/env bash

. ./setdir.sh

NAME=bnf
MARC_DIR=${BASE_INPUT_DIR}/bnf
TYPE_PARAMS="--emptyLargeCollectors --schemaType UNIMARC"
MASK=P174_*.UTF8

. ./common-script
