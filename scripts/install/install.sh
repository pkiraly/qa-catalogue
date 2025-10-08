#!/usr/bin/env bash
#
# download the minimum set of files necessary for run the dockerized QA Catalogue
#

# create directories
mkdir docker input output logs web-config solr-data docker/solr-entrypoint

# download necessary files
wget --inet4-only --quiet --output-document=docker-compose.yml \
  https://raw.githubusercontent.com/pkiraly/qa-catalogue/main/docker-compose-slim.yml
wget --inet4-only --quiet \
  https://raw.githubusercontent.com/pkiraly/qa-catalogue/main/scripts/install/qa-catalogue.env
wget --inet4-only --quiet --output-document=web-config/configuration.cnf \
  https://raw.githubusercontent.com/pkiraly/qa-catalogue/main/scripts/install/web-configuration.cnf
wget --inet4-only --quiet --output-document=docker/qa-catalogue \
  https://raw.githubusercontent.com/pkiraly/qa-catalogue/main/docker/qa-catalogue
wget --inet4-only --quiet --output-document=docker/solr-entrypoint/copy-configsets.sh \
  https://raw.githubusercontent.com/pkiraly/qa-catalogue/main/docker/solr-entrypoint/copy-configsets.sh
chmod +x docker/qa-catalogue


