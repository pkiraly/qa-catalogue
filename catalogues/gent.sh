#!/usr/bin/env bash
# Universiteitsbibliotheek Gent
# https://lib.ugent.be/

. ./setdir.sh

NAME=gent
# TYPE_PARAMS="--marcVersion GENT"
# MARC_DIR=${BASE_INPUT_DIR}/gent/marc/2019-06-05
# MASK=*.mrc
TYPE_PARAMS="--marcVersion GENT --alephseq"
# MARC_DIR=${BASE_INPUT_DIR}/gent/marc/2020-05-27
# MASK=*.export
# MARC_DIR=${BASE_INPUT_DIR}/gent/marc/2021-01-02
# MASK=rug01.backup*
MASK=rug01.backup.2023-11-11.gz

. ./common-script
