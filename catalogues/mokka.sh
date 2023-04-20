#!/usr/bin/env bash

. ./setdir.sh

NAME=mokka
TYPE_PARAMS="--marcxml"
# TYPE_PARAMS="--marcVersion SZTE"
MARC_DIR=${BASE_INPUT_DIR}/mokka
MASK=all.xml

. ./common-script
