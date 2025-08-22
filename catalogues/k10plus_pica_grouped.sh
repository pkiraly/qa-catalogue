#!/usr/bin/env bash
# K10plus-Verbundkatalog (PICA records with library holdings)
# https://opac.k10plus.de

. ./setdir.sh

SCHEMA=PICA
TYPE_PARAMS="$TYPE_PARAMS --schemaType PICA"
TYPE_PARAMS="$TYPE_PARAMS --marcFormat PICA_NORMALIZED"
TYPE_PARAMS="$TYPE_PARAMS --emptyLargeCollectors"
TYPE_PARAMS="$TYPE_PARAMS --groupBy 001@\$0"
TYPE_PARAMS="$TYPE_PARAMS --groupListFile src/main/resources/k10plus-libraries-by-unique-iln.txt"
TYPE_PARAMS="$TYPE_PARAMS --ignorableFields 001@,001E,001L,001U,001U,001X,001X,002V,003C,003G,003Z,008G,017N,020F,027D,031B,037I,039V,042@,046G,046T,101@,101E,101U,102D,201E,201U,202D,1...,2..."
#TYPE_PARAMS="$TYPE_PARAMS --ignorableIssueTypes undefinedField"
TYPE_PARAMS="$TYPE_PARAMS --allowableRecords base64:"$(echo '002@.0 !~ "^L" && 002@.0 !~ "^..[iktN]" && (002@.0 !~ "^.v" || 021A.a?)' | base64 -w 0)
# TYPE_PARAMS="$TYPE_PARAMS --solrUrl http://localhost:8983/solr/k10plus_pica_grouped"
TYPE_PARAMS="$TYPE_PARAMS --solrForScoresUrl http://localhost:8983/solr/k10plus_pica_grouped_validation"
TYPE_PARAMS="$TYPE_PARAMS --indexWithTokenizedField"
TYPE_PARAMS="$TYPE_PARAMS --indexFieldCounts --indexSubfieldCounts"
TYPE_PARAMS="$TYPE_PARAMS --fieldPrefix bib"
# MASK=sample.pica
# =kxp-title_2022-09-30-groupped.dat.gz
MASK=${MASK:=pica-with-holdings-info-1K.dat} # if not set in setdir.sh

. ./common-script
