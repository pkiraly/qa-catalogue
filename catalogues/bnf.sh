#!/usr/bin/env bash
# Bibliothèque nationale de France
# https://www.bnf.fr

. ./setdir.sh

NAME=bnf
TYPE_PARAMS="--emptyLargeCollectors --schemaType UNIMARC"
MASK=P174_*.UTF8

. ./common-script
