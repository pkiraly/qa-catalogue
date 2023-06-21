#!/bin/bash
set -ueo pipefail

. ./setdir.sh

options=$(getopt -o n:p:m:c:v:d:a:u:s: --long name:,params:,mask:,catalogue:,version:,input-dir:analyses:update:schema: -- "$@")
[ $? -eq 0 ] || { 
    echo "Incorrect options provided"
    exit 1
}
eval set -- "$options"

MARC_DIR=${BASE_INPUT_DIR}
NAME=qa-catalogue
SCHEMA=MARC
while true; do
    case "$1" in
    -a|--analyses) ANALYSES=$2 ; shift;;
    -c|--catalogue) CATALOGUE=$2 ; shift;;
    -d|--input-dir) MARC_DIR=${BASE_INPUT_DIR}/$2 ; shift;;
    -m|--mask) MASK=$2 ; shift;;
    -n|--name) NAME=$2 ; shift;;
    -p|--params) TYPE_PARAMS=$2 ; shift;;
    -s|--schema) SCHEMA=$2 ; shift;;
    -u|--update) UPDATE=$2 ; shift;;
    -v|--version) VERSION=$2 ; shift;;
    -s|--schema) SCHEMA=$2 ; shift;;
    --)
        shift
        break
        ;;
    esac
    shift
done

cat << EOF
configuration:
---------------------------
ANALYSES:    ${ANALYSES:-}
CATALOGUE:   ${CATALOGUE:-}
MARC_DIR:    ${MARC_DIR:-}
MASK:        ${MASK:-}
NAME:        ${NAME:-}
TYPE_PARAMS: ${TYPE_PARAMS:-}
SCHEMA:      ${SCHEMA:-}
UPDATE:      ${UPDATE:-}
VERSION:     ${VERSION:-}
---------------------------
EOF

if [[ "${CATALOGUE:-}" != "" ]]; then
  FILE=/var/www/html/qa-catalogue/configuration.cnf
  if [[ -f $FILE ]]; then
    count=$(grep -cP "^catalogue=" $FILE)
    if [[ $count == 1 ]]; then
      grep -vP "^catalogue=" $FILE > /tmp/catalogue
      mv /tmp/catalogue $FILE
    fi
    echo "catalogue=$CATALOGUE" >> $FILE
  fi
fi

. ./common-script ${1:-}

exit 0
