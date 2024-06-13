#!/usr/bin/env bash
# Biblioteka Narodowa (Polish National Library)
# https://bn.org.pl/

. ./setdir.sh

NAME=bnpl
# TYPE_PARAMS="--marcVersion GENT"
TYPE_PARAMS=" --emptyLargeCollectors --indexWithTokenizedField"
MASK=bibs-all.marc.gz

. ./common-script
