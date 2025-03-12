#!/usr/bin/env bash
# Biblioteca Nationala a Romaniei
# https://www.bibnat.ro/

. ./setdir.sh
NAME=bnr
# TYPE_PARAMS="--marcVersion GENT"
TYPE_PARAMS=" --emptyLargeCollectors"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MASK=bnr.*.mrc

. ./common-script
