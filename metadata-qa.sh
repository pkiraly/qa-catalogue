#!/bin/bash
set -ueo pipefail

usage() {
  cat << EOF
Usage: $0 [options] <command>

QA catalogue for analysing library data

Options:
  -a --analyses   which commands to run with command 'all-analyses'
  -c --catalogue  ???
  -n --name       name of the analysis (default: qa-catalogue)
  -i --input      input directory (default: ./input)
  -d --input-dir  subdirectory of input directory to read files from (default: the NAME)
  -m --mask       input filename or glob (default: *)
  -o --output     output directory. Result are put into subdirectory NAME and _reports (default: ./output)
  -p --params     additional parameters passed to analyses
  -s --schema     record schema (default: MARC21, also supported: PICA)
  -u --update     last modification time of input files (default: timestamps)
  -v --version    ???

EOF
  . ./common-script help
  exit
}

# optionally load default values
[ -f ./setdir.sh ] && . ./setdir.sh

# default values, if not set
BASE_INPUT_DIR=${BASE_INPUT_DIR:-./input}
BASE_OUTPUT_DIR=${BASE_OUTPUT_DIR:-./output}
NAME=${NAME:-qa-catalogue}

# parse command line options
options=$(getopt -o a:c:d:i:m:n:o:p:s:u:v --long analyses:,catalogue:,input-dir:,input:,mask:,name:,output:,params:,schema:,update:,version: -- "$@")

eval set -- "$options"

while true; do
    case "$1" in
    -a|--analyses) ANALYSES=$2 ; shift;;
    -c|--catalogue) CATALOGUE=$2 ; shift;;
    -d|--input-dir) MARC_DIR=${BASE_INPUT_DIR}/$2 ; shift;;
    -i|--input) BASE_INPUT_DIR=$2 ; shift;;
    -m|--mask) MASK=$2 ; shift;;
    -n|--name) NAME=$2 ; shift;;
    -o|--output) BASE_OUTPUT_DIR=$2 ; shift;;
    -p|--params) TYPE_PARAMS=$2 ; shift;;
    -s|--schema) SCHEMA=$2 ; shift;;
    -u|--update) UPDATE=$2 ; shift;;
    -v|--version) VERSION=$2 ; shift;;
    --)
        shift
        break
        ;;
    esac
    shift
done

[ "${1:-help}" == "help" ] && usage

cat << EOF
configuration:
---------------------------
ANALYSES:                 ${ANALYSES:-}
CATALOGUE:                ${CATALOGUE:-}
MARC_DIR (input-dir):     ${MARC_DIR:-}
BASE_INPUT_DIR (input):   ${BASE_INPUT_DIR}
MASK:                     ${MASK:-}
NAME:                     ${NAME}
BASE_OUTPUT_DIR (output): ${BASE_OUTPUT_DIR}
SCHEMA:                   ${SCHEMA:-}
TYPE_PARAMS (params):     ${TYPE_PARAMS:-}
UPDATE:                   ${UPDATE:-}
VERSION:                  ${VERSION:-}
---------------------------
EOF

if [[ "${CATALOGUE:-}" != "" ]]; then
  FILE=/var/www/html/qa-catalogue/configuration.cnf
  if [[ -f $FILE ]]; then
     HAS_CATALOGUE=$(grep -c 'catalogue=' $FILE)
    if [[ $HAS_CATALOGUE == 1 ]]; then
      grep -vP "^catalogue=" $FILE > /tmp/catalogue
      mv /tmp/catalogue $FILE
    fi
    echo "catalogue=$CATALOGUE" >> $FILE
  fi
fi

. ./common-script ${1:-}

exit 0
