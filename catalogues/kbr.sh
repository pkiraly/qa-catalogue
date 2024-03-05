#!/usr/bin/env bash
# KBR (Koninklijke Bibliotheek van België/Bibliothèque royale de Belgique)
# https://www.kbr.be/

. ./setdir.sh

NAME=kbr
MARC_DIR=${BASE_INPUT_DIR}/kbr/current
TYPE_PARAMS="--emptyLargeCollectors --marcVersion KBR --marcxml --fixKbr --ignorableFields 590,591,592,593,594,595,596,659,900,911,912,916,940,941,942,944,945,946,948,949,950,951,952,953,954,970,971,972,973,975,977,988,989"
MASK=kbr-*.gz

. ./common-script
