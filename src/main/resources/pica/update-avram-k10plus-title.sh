#!/usr/bin/env bash

command -v jq >/dev/null 2>&1 || { echo >&2 "Missing command jq"; exit 1; }

# See <https://github.com/pkiraly/metadata-qa-marc/issues/193> why repeatable is changed
curl "https://format.k10plus.de/avram.pl?profile=k10plus-title" \
    | jq -S '(.fields[]|select(.subfields.U and .subfields.T)).repeatable=true' > avram-k10plus-title.json

