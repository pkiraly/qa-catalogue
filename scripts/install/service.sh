#!/usr/bin/env bash
#
# Managing docker services and files for QA Catalogue
#
set -e
ARGV="$@"

up () {
  echo "Creating docker containers..."
  docker compose --env-file qa-catalogue.env up -d
}

down () {
  echo "Removing docker containers..."
  docker compose --env-file qa-catalogue.env down
}

start () {
  echo "Starting docker containers..."
  docker compose --env-file qa-catalogue.env start solr
  docker compose --env-file qa-catalogue.env start cli
  docker compose --env-file qa-catalogue.env start app
}

stop () {
  echo "Stoping docker containers..."
  docker compose --env-file qa-catalogue.env stop cli
  docker compose --env-file qa-catalogue.env stop app
  docker compose --env-file qa-catalogue.env stop solr
}

uninstall () {
  echo "Removing docker images and clean up this directory..."
  ids=$(docker image ls --filter reference=*/pkiraly/qa-catalogue* -q)
  if [[ "$ids" != "" ]]; then
    docker rmi $ids
  fi

  ids=$(docker images solr:9.6.1 -q)
  if [[ "$ids" != "" ]]; then
    docker rmi $ids
  fi

  sudo rm -rf *
}

usage () {
  echo "Managing docker services and files for QA Catalogue"
  echo ""
  echo "usage:"
  echo "$0 <command>"
  echo "commands:"
  echo "- up|u - create and start docker containers"
  echo "- down|d - stop and remove docker containers"
  echo "- start - start docker containers"
  echo "- stop - stop docker containers"
  echo "- uninstall - remove docker images and all the files in the current directory"
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
    start)
        start
        exit
    ;;
    stop)
        stop
        exit
    ;;
    uninstall)
        uninstall
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
