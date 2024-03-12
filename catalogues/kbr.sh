#!/usr/bin/env bash
# KBR (Koninklijke Bibliotheek van België/Bibliothèque royale de Belgique)
# https://www.kbr.be/

. ./setdir.sh

NAME=kbr
# MARC_DIR=${BASE_INPUT_DIR}/kbr/current
MARC_DIR=${BASE_INPUT_DIR}/current
TYPE_PARAMS="--emptyLargeCollectors --marcVersion KBR --marcxml --fixKbr"
TYPE_PARAMS="${TYPE_PARAMS} --ignorableFields 590,591,592,593,594,595,596,659,900,911,912,916,940,941,942,944,945,946,948,949,950,951,952,953,954,970,971,972,973,975,977,988,989"
# TYPE_PARAMS="${TYPE_PARAMS} --solrForScoresUrl http://localhost:8983/solr/kbr_scores"
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField"
TYPE_PARAMS="${TYPE_PARAMS} --indexFieldCounts"
MASK=kbr-*.gz

. ./common-script
