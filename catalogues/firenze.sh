#!/usr/bin/env bash
# Biblioteca Nazionale Centrale di Firenze
# https://www.bncf.firenze.sbn.it/

. ./setdir.sh

NAME=firenze
TYPE_PARAMS="--emptyLargeCollectors"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MASK=firenze.*.mrc.gz

. ./common-script
