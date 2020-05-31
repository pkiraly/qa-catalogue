#!/bin/bash

. ./setdir.sh

options=$(getopt -o n:p:m:c: --long name:,params:,mask:,catalogue: -- "$@")
[ $? -eq 0 ] || { 
    echo "Incorrect options provided"
    exit 1
}
eval set -- "$options"

MARC_DIR=${BASE_INPUT_DIR}
NAME=metadata-qa
while true; do
    case "$1" in
    #-b) COLOR=BLUE ; ;;
    #-r) COLOR=RED ; ;;
    #-g) COLOR=GREEN ; ;;
    -n|--name) NAME=$2 ; shift;;
    -p|--params) TYPE_PARAMS=$2 ; shift;;
    -m|--mask) MASK=$2 ; shift;;
    -c|--catalogue) CATALOGUE=$2 ; shift;;
    --)
        shift
        break
        ;;
    esac
    shift
done

if [[ "$CATALOGUE" != "" ]]; then
  FILE=/var/www/html/metadata-qa/configuration.cnf
  count=$(grep -c catalogue $FILE)
  if [[ $count == 1 ]]; then
    grep -v catalogue $FILE > /tmp/catalogue
    mv /tmp/catalogue $FILE
  fi
  echo "catalogue=$CATALOGUE" >> $FILE

  FILE=/var/www/html/metadata-qa/configuration.js
  count=$(grep -c catalogue $FILE)
  if [[ $count == 1 ]]; then
    grep -v catalogue $FILE > /tmp/catalogue
    mv /tmp/catalogue $FILE
  fi
  echo "var catalogue = '$CATALOGUE'" >> $FILE
fi

. ./common-script $1

exit 0;
