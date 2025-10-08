#!/usr/bin/env bash

set -e
ARGV="$@"

up () {
  echo "up()"
  docker compose --env-file qa-catalogue.env up -d
}

down () {
  docker compose --env-file qa-catalogue.env down
}

clear_files () {
  docker rmi $(docker image ls --filter reference=*/pkiraly/qa-catalogue* -q)
  docker rmi $(docker images solr:9.6.1 -q)
  sudo rm -rf *
}

usage () {
  echo "$0 <command>"
  echo "up|u - start docker services"
  echo "down|d - stop docker services"
  echo "clear-files|c - remove docker images and all the files in the current directory"
}

if [ $# -eq 1 ]; then
  case $1 in
    up|u)
        up
        exit
    ;;
    down|d)
        down
        exit
    ;;
    clear-files|c)
        clear_files
        exit
    ;;
    help|h)
        usage
        exit
    ;;
    *)
        echo "Unrecognised command: $1"
        exit
    ;;
  esac
fi
