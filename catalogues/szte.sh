#!/usr/bin/env bash
# A Szegedi Tudományegyetem Klebelsberg Kuno Könyvtára
# http://www.ek.szte.hu/

. ./setdir.sh

NAME=szte
TYPE_PARAMS="--marcVersion SZTE"
MARC_DIR=${BASE_INPUT_DIR}/szte/marc/2019-04-25
MASK=*.mrc

. ./common-script
