#!/usr/bin/env bash

up() {
  docker compose --env-file qa-catalogue.env up -d
}

down() {
  docker compose --env-file qa-catalogue.env down
}

clear_files() {
  docker rmi $(docker image ls --filter reference=*/pkiraly/qa-catalogue* -q)
  docker rmi $(docker images solr:9.6.1 -q)
  sudo rm -rf *
}

if [ $# -eq 1 ]; then
  case $1 in
    -up|-u)
        up
        exit $?
    ;;
    -down|-d)
        down
        exit $?
    ;;
    -clear-files|-c)
        clear_files
        exit
    ;;
  esac
fi