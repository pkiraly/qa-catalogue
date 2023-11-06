#!/usr/bin/env bash
#
#--------------------------------
# retrieve subjects from K10plus
#--------------------------------

curl -s https://format.k10plus.de/avram.pl?profile=k10plus-title \
  | jq -r '.fields[] | select(.tag | match("04[45]|041A")) | [.tag, .occurrence, .label] | @tsv' -r \
  > k10plus-subjects.tsv


