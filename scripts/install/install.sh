#!/usr/bin/env bash
#
# download the minimum set of files necessary for run the dockerized QA Catalogue
#

# create directories
mkdir docker input output web-config

# download necessary files
wget --inet4-only --quiet https://raw.githubusercontent.com/pkiraly/qa-catalogue/main/docker-compose.yml
wget --inet4-only --quiet https://raw.githubusercontent.com/pkiraly/qa-catalogue/main/docker-compose-slim.yml
wget --inet4-only --quiet https://raw.githubusercontent.com/pkiraly/qa-catalogue/main/scripts/install/qa-catalogue.env
wget --inet4-only --quiet \
  --output-document=docker/configuration.cnf \
  https://raw.githubusercontent.com/pkiraly/qa-catalogue/main/scripts/install/web-configuration.cnf
cd docker
wget --inet4-only --quiet https://raw.githubusercontent.com/pkiraly/qa-catalogue/main/docker/qa-catalogue
chmod +x qa-catalogue
cd ..

