#!/bin/bash

# run this script with name of your catalogue as argument
# if no argument given defaults to test

APPDIR="/home/qa-catalogue/qa-catalogue"
CATALOGUE=${1:-test}
DATADIR="${APPDIR}/input/${CATALOGUE}/marc"
CHECKFILE="${DATADIR}/check_file.txt"
RUNNINGFILE="${APPDIR}/running_${CATALOGUE}_analyses.txt"

find ${DATADIR} -newer "$CHECKFILE" >/tmp/newfiles$$

if [[ -s "/tmp/newfiles$$" ]] ; then
  echo "files changed: "
  cat "/tmp/newfiles$$"
  echo "$(date) Now start qa catalogue analyses"
  if [[ -f "$RUNNINGFILE" ]]; then
    echo "$(date) Oops, there are analyses still running! Try again later."
  else
    touch "$RUNNINGFILE"
    cd $APPDIR
    docker exec -i metadata-qa-marc ./catalogues/${CATALOGUE}.sh all
    echo "$(date) Finished analyses for ${CATALOGUE}!"
    rm "$RUNNINGFILE"
    touch "$CHECKFILE"
    rm "/tmp/newfiles$$"
  fi
else
  echo "No files changed"
  touch "$CHECKFILE"
  rm "/tmp/newfiles$$"
fi
