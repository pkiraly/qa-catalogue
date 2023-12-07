#!/usr/bin/env bash

. ./setdir.sh
NAME=nls
MARC_DIR=${BASE_INPUT_DIR}/nls
TYPE_PARAMS="--marcxml --emptyLargeCollectors --indexWithTokenizedField --indexFieldCounts --solrForScoresUrl http://localhost:8983/solr/nls_validation"
MASK=NBS_v2_validated_marcxml.xml.gz

. ./common-script
