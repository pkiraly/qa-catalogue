#!/usr/bin/env bash
# European Literary Bibliography
# https://literarybibliography.eu/

. ./setdir.sh
NAME=elb
# TYPE_PARAMS="--marcVersion GENT"
TYPE_PARAMS="--marcFormat MARCMAKER --emptyLargeCollectors"
# index parameters
TYPE_PARAMS="${TYPE_PARAMS} --indexWithTokenizedField --indexFieldCounts --indexSubfieldCounts"

# SHACL
# TYPE_PARAMS="${TYPE_PARAMS} --shaclConfigurationFile /home/pkiraly/git/qa-catalogue/scripts/translations/translations-shacl.yml"
# TYPE_PARAMS="${TYPE_PARAMS} --shaclOutputType STATUS"

MASK=*.mrk
# test:
# MASK=es_ksiazki__08-02-2024.mrk

. ./common-script
