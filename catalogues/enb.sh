#!/usr/bin/env bash
# Estonian National Bibliography
# https://digilab.rara.ee/en/datasets/estonian-national-bibliography/

. ./setdir.sh

NAME=enb
# MARC_DIR=${BASE_INPUT_DIR}/kbr/current
# MARC_DIR=${BASE_INPUT_DIR}/current
TYPE_PARAMS="--emptyLargeCollectors --marcxml"
# TYPE_PARAMS="${TYPE_PARAMS} --ignorableFields 590,591,592,593,594,595,596,659,900,911,912,916,940,941,942,944,945,946,948,949,950,951,952,953,954,970,971,972,973,975,977,988,989"
# TYPE_PARAMS="${TYPE_PARAMS} --solrForScoresUrl http://localhost:8983/solr/kbr_scores"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"


# TYPE_PARAMS="${TYPE_PARAMS} --shaclConfigurationFile /opt/shacl4bib/kbr-full.yaml"
# TYPE_PARAMS="${TYPE_PARAMS} --shaclConfigurationFile /home/qa_admin/git/data-validation-rules/kbr-full.yaml"
# TYPE_PARAMS="${TYPE_PARAMS} --shaclOutputType STATUS"

MASK=${MASK:-0*.xml.gz}

. ./common-script
