#!/usr/bin/env bash

. ./setdir.sh
NAME=k10plus_pica
MARC_DIR=${BASE_INPUT_DIR}/k10plus_pica
TYPE_PARAMS="--schemaType PICA --marcFormat PICA_PLAIN --emptyLargeCollectors"
TYPE_PARAMS="$TYPE_PARAMS --ignorableFields 001@,001E,001L,001U,001U,001X,001X,002V,003C,003G,003Z,008G,017N,020F,027D,031B,037I,039V,042@,046G,046T"
#TYPE_PARAMS="$TYPE_PARAMS --allowableRecords '002@.0 !~ \"^L\",002@.0 !~ \"^..[iktN]\",002@.0 !~ \"^.v\",021A.a?'"
echo $TYPE_PARAMS
MASK=sample.pica

. ./common-script

if [[ "$1" != "help" ]]; then
  echo "DONE"
fi

exit 0
