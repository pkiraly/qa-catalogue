#!/usr/bin/env bash
#
# download the minimum set of files necessary for run the dockerized QA Catalogue
#

curl -fsSL --output docker-compose.yml https://raw.githubusercontent.com/pkiraly/qa-catalogue/main/docker-compose.yml
mkdir docker
cd docker
curl -fsSL --output qa-catalogue https://raw.githubusercontent.com/pkiraly/qa-catalogue/main/docker/qa-catalogue
chmod +x qa-catalogue
cd ..