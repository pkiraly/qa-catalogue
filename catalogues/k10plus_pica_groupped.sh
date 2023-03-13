#!/usr/bin/env bash

. ./setdir.sh
NAME=k10plus_pica_groupped
MARC_DIR=${BASE_INPUT_DIR}/k10plus_pica_groupped
SCHEMA=PICA
TYPE_PARAMS="--schemaType PICA --marcFormat PICA_NORMALIZED --emptyLargeCollectors"
TYPE_PARAMS="$TYPE_PARAMS --groupBy 001@\$0"
TYPE_PARAMS="$TYPE_PARAMS --groupListFile src/main/resources/kxp-uniq-library-names.tsv"
TYPE_PARAMS="$TYPE_PARAMS --ignorableFields 001@,001E,001L,001U,001U,001X,001X,002V,003C,003G,003Z,008G,017N,020F,027D,031B,037I,039V,042@,046G,046T,101@,101E,101U,102D,201E,201U,202D,1...,2..."
TYPE_PARAMS="$TYPE_PARAMS --ignorableIssueTypes undefinedField"
TYPE_PARAMS="$TYPE_PARAMS --allowableRecords base64:"$(echo '002@.0 !~ "^L" && 002@.0 !~ "^..[iktN]" && (002@.0 !~ "^.v" || 021A.a?)' | base64 -w 0)
# MASK=sample.pica
MASK=pica-with-holdings-info-1K.dat

. ./common-script

if [[ "$1" != "help" ]]; then
  echo "DONE"
fi

exit 0
