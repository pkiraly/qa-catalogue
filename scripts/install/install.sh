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
wget --inet4-only --quiet \
  https://raw.githubusercontent.com/pkiraly/qa-catalogue/main/scripts/install/service.sh
wget --inet4-only --quiet --output-document=web-config/configuration.cnf \
  https://raw.githubusercontent.com/pkiraly/qa-catalogue/main/scripts/install/web-configuration.cnf
wget --inet4-only --quiet --output-document=docker/qa-catalogue \
  https://raw.githubusercontent.com/pkiraly/qa-catalogue/main/docker/qa-catalogue
wget --inet4-only --quiet --output-document=docker/solr-entrypoint/copy-configsets.sh \
  https://raw.githubusercontent.com/pkiraly/qa-catalogue/main/docker/solr-entrypoint/copy-configsets.sh

# making it executable
chmod +x docker/qa-catalogue
chmod +x service.sh
sudo chown -R 8983:8983 ./solr-data && sudo chmod -R 777 ./solr-data

echo "Welcome to QA catalogue"
echo ""
echo "The stage is yours! Typical next steps follow as"
echo ""
echo "  1) copy bibliographical records to `input` directory, adjust 'qa-catalogue.env', and 'web-config/configuration.cnf'"
echo "  2) start services with './service.sh up'"
echo "  3) run analyses with 'docker/qa-catalogue ...'"
echo "  4) check it at http://localhost:90"
echo "  if you do not need it anymore"
echo "  5) shut services down with './service.sh down''"
echo "  6) remove all files with './service.sh clear-files'"
echo ""
echo "  more info: https://github.com/pkiraly/qa-catalogue"
echo "Happy analyses!"
