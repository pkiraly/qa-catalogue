#!/usr/bin/env bash
# The National Bibliography of Scotland (from the National Library of Scotland)
# https://www.nls.uk/

. ./setdir.sh

NAME=nls
TYPE_PARAMS="--marcxml --emptyLargeCollectors --indexWithTokenizedField --indexFieldCounts --solrForScoresUrl http://localhost:8983/solr/nls_validation"
MASK=NBS_v2_validated_marcxml.xml.gz

. ./common-script
