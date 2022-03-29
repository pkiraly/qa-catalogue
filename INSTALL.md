# QA catalogue Installation Guide

This guide describes how to install components in an Ubuntu machine.

## Prerequisites

The software needs Java 11, R and PHP.

Install system components. Some of these probably already

```bash
sudo apt-get update
# Install add-apt-repository command
apt-get install -y --no-install-recommends software-properties-common
# add PPA with pre-compiled cran packages
sudo add-apt-repository -y ppa:openjdk-r/ppa
sudo add-apt-repository -y ppa:marutter/rrutter3.5
# install general OS tools
sudo apt-get install -y --no-install-recommends apt-utils nano jq curl openssl less wget unzip
# install Java
sudo apt-get install openjdk-11-jre-headless
# Install R
sudo apt-get install r-base
# Install R packages from ppa:marutter
sudo apt-get install r-cran-tidyverse r-cran-stringr r-cran-gridextra
# Install sqlite3
sudo apt-get install sqlite3 r-cran-rsqlite
# Install PHP
sudo apt-get install php php-sqlite3
```

### Install Apache Solr

```bash
export SOLR_VERSION=8.11.1
cd /opt
wget http://archive.apache.org/dist/lucene/solr/${SOLR_VERSION}/solr-${SOLR_VERSION}.zip
unzip -q solr-${SOLR_VERSION}.zip
rm solr-${SOLR_VERSION}.zip
ln -s solr-${SOLR_VERSION} solr
```

## Download and Configure QA catalogue

```bash
export VERSION=0.5.0
wget https://github.com/pkiraly/metadata-qa-marc/releases/download/v${VERSION}/metadata-qa-marc-${VERSION}-release.zip
unzip metadata-qa-marc-$VERSION-release.zip
cd metadata-qa-marc-$VERSION
cp setdir.sh.template setdir.sh
```
Note: `VERSION` points here to the latest released version at time of writing. SNAPSHOT versions can be download from https://oss.sonatype.org/content/repositories/snapshots/de/gwdg/metadataqa/metadata-qa-marc/

Now edit the following lines in `setdir.sh`

```bash
BASE_INPUT_DIR=your/path
BASE_OUTPUT_DIR=your/path
```

`BASE_INPUT_DIR` is the directory where your MARC21 files exists, `BASE_OUTPUT_DIR` is where QA catalogue will put 
the results of the analyses.

Ready. Now you can start using it.