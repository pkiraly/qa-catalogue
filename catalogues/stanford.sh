#!/usr/bin/env bash

. ./setdir.sh

NAME=stanford
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MASK=*.marc

. ./common-script
