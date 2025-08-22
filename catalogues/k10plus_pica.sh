#!/usr/bin/env bash
# K10plus-Verbundkatalog (PICA records)
# https://opac.k10plus.de

. ./setdir.sh

NAME=k10plus_pica
SCHEMA=PICA
TYPE_PARAMS="--emptyLargeCollectors"
TYPE_PARAMS="$TYPE_PARAMS --ignorableFields 001@,001E,001L,001U,001U,001X,001X,002V,003C,003G,003Z,008G,017N,020F,027D,031B,037I,039V,042@,046G,046T,101@,101E,101U,102D,201E,201U,202D,1...,2..."
#TYPE_PARAMS="$TYPE_PARAMS --ignorableIssueTypes undefinedField"
TYPE_PARAMS="$TYPE_PARAMS --allowableRecords base64:"$(echo '002@.0 !~ "^L" && 002@.0 !~ "^..[iktN]" && (002@.0 !~ "^.v" || 021A.a?)' | base64 -w 0)
# MASK=sample.pica
MASK=${MASK:=kxp-sample-title_2022-09_30.dat} # if not set in setdir.sh
# MASK=kxp-title_2022-09-30.dat
# MASK=small.dat

. ./common-script
