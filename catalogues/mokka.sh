#!/usr/bin/env bash
# Magyar Országos Közös Katalógus
# http://mokka.hu/

. ./setdir.sh

NAME=mokka
TYPE_PARAMS="--marcxml --emptyLargeCollectors --indexWithTokenizedField"
# TYPE_PARAMS="--marcVersion SZTE"
MASK=all.xml.gz

. ./common-script
