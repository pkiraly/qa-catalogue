#!/usr/bin/env bash
# Biblioteca Nazionale Centrale di Firenze
# https://www.bncf.firenze.sbn.it/

. ./setdir.sh

NAME=firenze
TYPE_PARAMS="--emptyLargeCollectors"
MARC_DIR=${BASE_INPUT_DIR}/firenze
MASK=firenze.*.mrc.gz

. ./common-script
