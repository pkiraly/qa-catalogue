#!/usr/bin/env bash
#
# download the minimum set of files necessary for run the dockerized QA Catalogue
#

# download necessary files
wget --inet4-only --quiet https://raw.githubusercontent.com/pkiraly/qa-catalogue/main/docker-compose.yml
wget --inet4-only --quiet https://raw.githubusercontent.com/pkiraly/qa-catalogue/main/docker-compose-slim.yml
wget --inet4-only --quiet https://raw.githubusercontent.com/pkiraly/qa-catalogue/main/scripts/install/qa-catalogue.env
mkdir docker
cd docker
wget --inet4-only --quiet https://raw.githubusercontent.com/pkiraly/qa-catalogue/main/docker/qa-catalogue
chmod +x qa-catalogue
cd ..

# create directories
mkdir input output web-config