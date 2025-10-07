#!/usr/bin/env bash
#
# download the minimum set of files necessary for run the dockerized QA Catalogue
#

wget -4 https://raw.githubusercontent.com/pkiraly/qa-catalogue/main/docker-compose.yml
mkdir docker
cd docker
wget -4 https://raw.githubusercontent.com/pkiraly/qa-catalogue/main/docker/qa-catalogue
chmod +x qa-catalogue
cd ..