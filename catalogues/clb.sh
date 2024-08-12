#!/usr/bin/env bash
# Česká literární bibliografie
# https://clb.ucl.cas.cz/

. ./setdir.sh

NAME=clb
TYPE_PARAMS="--marcxml --emptyLargeCollectors --marcVersion NKCR"
MASK=ucloall.xml.gz

. ./common-script
