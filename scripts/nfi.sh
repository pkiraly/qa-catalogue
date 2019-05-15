#!/usr/bin/env bash

. ./setdir.sh
NAME=nfi
TYPE_PARAMS="--marcVersion FENNICA"
MARC_DIR=${DIR}/nationallibrary.fi
MASK=*.mrc

. ./common-script

echo "DONE"
exit 0
