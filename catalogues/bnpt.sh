#!/usr/bin/env bash
# Biblioteca Nacional de Portugal (BNP)
# web: https://www.bnportugal.gov.pt/
# data: https://opendata.bnportugal.gov.pt/downloads.htm

. ./setdir.sh

NAME=bnpt
# TYPE_PARAMS="--marcVersion GENT"
TYPE_PARAMS="--schemaType UNIMARC --marcxml --emptyLargeCollectors"
MARC_DIR=${BASE_INPUT_DIR}/bnpt
MASK=bibliographics_*.xml

. ./common-script
