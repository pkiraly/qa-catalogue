#!/usr/bin/env bash
# Libris, the Swedish national union catalogue
# https://libris.kb.se/

. ./setdir.sh

NAME=libris
TYPE_PARAMS="--emptyLargeCollectors --marcxml --indexWithTokenizedField"
MASK=sw-?.xml.gz

. ./common-script
