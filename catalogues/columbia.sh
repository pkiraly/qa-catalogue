#!/usr/bin/env bash

. ./setdir.sh

NAME=columbia
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"
MASK=*.mrc

. ./common-script
