#!/bin/bash

. ./setdir.sh

options=$(getopt -o n:p:m: --long name:,params:,mask: -- "$@")
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
    --name) NAME=$2 ; shift;;
    --params) TYPE_PARAMS=$2 ; shift;;
    --mask) MASK=$2 ; shift;;
    --)
        shift
        break
        ;;
    esac
    shift
done

. ./common-script $1

exit 0;
