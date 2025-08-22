#!/usr/bin/env bash

. ./setdir.sh

NAME=michigan
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MASK=*.marc

. ./common-script
