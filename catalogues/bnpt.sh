#!/usr/bin/env bash
# Biblioteca Nacional de Portugal (BNP)
# web: https://www.bnportugal.gov.pt/
# data: https://opendata.bnportugal.gov.pt/downloads.htm

. ./setdir.sh

NAME=bnpt
# TYPE_PARAMS="--marcVersion GENT"
TYPE_PARAMS="--schemaType UNIMARC --marcxml --emptyLargeCollectors"
TYPE_PARAMS="$TYPE_PARAMS --solrForScoresUrl http://localhost:8983/solr/bnpt_validation"
TYPE_PARAMS="$TYPE_PARAMS --indexWithTokenizedField"
TYPE_PARAMS="$TYPE_PARAMS --indexFieldCounts"

MASK=bibliographics_*.xml

. ./common-script
