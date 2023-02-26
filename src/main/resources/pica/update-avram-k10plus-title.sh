#!/usr/bin/env bash

command -v jq >/dev/null 2>&1 || { echo >&2 "Missing command jq"; exit 1; }

TIMESTAMP=$(date +"%Y-%m-%d")
# See <https://github.com/pkiraly/metadata-qa-marc/issues/193> why repeatable is changed
curl "https://format.k10plus.de/avram.pl?profile=k10plus-title" \
    | jq -S '(.fields[]|select(.subfields.U and .subfields.T)).repeatable=true' \
    | jq -S '(.fields[]|select(.occurrence=="00")).occurrence=null' \
    > avram-k10plus-title-$TIMESTAMP.json

