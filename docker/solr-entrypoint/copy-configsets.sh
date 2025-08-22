#!/bin/bash
# - - - -
# copy the standard configsets to docker's SOLR_HOME
# - - - -
set -e

if [[ ! -d /var/solr/data/configsets ]]; then
  cp -r /opt/solr/server/solr/configsets /var/solr/data/
fi
