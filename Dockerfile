FROM openjdk:11-jdk AS build
RUN apt-get update && apt-get install -y --no-install-recommends \
  maven \
  && rm -rf /var/lib/apt/lists/*
RUN mkdir -p /opt/qa-catalogue
COPY . /opt/qa-catalogue
WORKDIR /opt/qa-catalogue
RUN mvn clean install

FROM ubuntu:22.04

LABEL maintainer="Péter Király <pkiraly@gwdg.de>, Ákos Takács <rimelek@rimelek.hu>, Jakob Voß <jakob.voss@gbv.de>"

LABEL description="QA catalogue - a metadata quality assessment tool for MARC based library catalogues."
# the Github repo labels
LABEL org.opencontainers.image.description="QA catalogue - a metadata quality assessment tool for MARC based library catalogues."
LABEL org.opencontainers.image.source=https://github.com/pkiraly/qa-catalogue
LABEL org.opencontainers.image.licenses="GNU General Public License v3.0"

ARG DEBIAN_FRONTEND=noninteractive
ARG QA_CATALOGUE_VERSION=0.8.0-SNAPSHOT
ARG QA_CATALOGUE_WEB_VERSION=main
ARG SOLR_VERSION=8.11.1
ARG SOLR_INSTALL_SOURCE=remote

# install R
ENV TZ=Etc/UTC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime \
 && echo $TZ > /etc/timezone

RUN apt-get update \
 # Install add-apt-repository command
 && apt-get install -y --no-install-recommends software-properties-common gnupg2 \
 # add PPA with pre-compiled cran packages
 && add-apt-repository -y ppa:openjdk-r/ppa \
 && echo "deb https://cloud.r-project.org/bin/linux/ubuntu focal-cran40/" > /etc/apt/sources.list.d/cran.list \
 && apt-key adv --keyserver keyserver.ubuntu.com --recv-keys E298A3A825C0D65DFD57CBB651716619E084DAB9 \
 && apt-get install -y --no-install-recommends \
      # install basic OS tools
      apt-utils \
      nano \
      jq \
      curl \
      wget \
      openssl \
      git \
      # install Java
      openjdk-11-jre-headless \
      # Install R
      r-base \
      # Install R packages from ppa:marutter
      r-cran-tidyverse \
      r-cran-stringr \
      r-cran-gridextra \
      r-cran-rsqlite \
      r-cran-httr \
      sqlite3 \
      less \
      # for Apache Solr
      lsof \
 && apt-get --assume-yes autoremove \
 && rm -rf /var/lib/apt/lists/*

# install qa-catalogue
COPY --from=build /opt/qa-catalogue/target/qa-catalogue-${QA_CATALOGUE_VERSION}-release.zip /opt
# COPY target/qa-catalogue-${QA_CATALOGUE_VERSION}-release.zip /opt

RUN cd /opt \
 && unzip qa-catalogue-${QA_CATALOGUE_VERSION}-release.zip \
 && rm qa-catalogue-${QA_CATALOGUE_VERSION}-release.zip \
 && mv qa-catalogue-${QA_CATALOGUE_VERSION} qa-catalogue \
 && mv /opt/qa-catalogue/setdir.sh.template /opt/qa-catalogue/setdir.sh \
 && sed -i.bak 's,BASE_INPUT_DIR=./input,BASE_INPUT_DIR=/opt/qa-catalogue/input,' /opt/qa-catalogue/setdir.sh \
 && sed -i.bak 's,BASE_OUTPUT_DIR=./output,BASE_OUTPUT_DIR=/opt/qa-catalogue/output,' /opt/qa-catalogue/setdir.sh \
 && mkdir -p /opt/qa-catalogue/input /opt/qa-catalogue/output

# install web application
RUN apt-get update \
 && apt-get install -y --no-install-recommends \
      locales \
      apache2 \
      php \
      php-sqlite3 \
      php-curl \
      php-yaml \
      php-intl \
      php-dom \
      unzip \
      composer \
      gettext \
 && locale-gen en_GB.UTF-8 && locale-gen de_DE.UTF-8 && locale-gen pt_BR.UTF-8 && locale-gen hu_HU.UTF-8 \
 && apt-get --assume-yes autoremove \
 && rm -rf /var/lib/apt/lists/* \
 && cd /var/www/html/ \
 && if [ "${QA_CATALOGUE_WEB_VERSION}" = "main" ]; then \
      curl -s -L https://github.com/pkiraly/qa-catalogue-web/archive/refs/heads/main.zip --output master.zip ; \
    elif [ "${QA_CATALOGUE_WEB_VERSION}" = "develop" ]; then \
      curl -s -L https://github.com/pkiraly/qa-catalogue-web/archive/refs/heads/develop.zip --output master.zip ; \
    else \
      curl -s -L https://github.com/pkiraly/qa-catalogue-web/archive/refs/tags/v${QA_CATALOGUE_WEB_VERSION}.zip --output master.zip ; \
    fi \
 && ls -la \
 && unzip -q master.zip \
 && rm master.zip \
 && mv qa-catalogue-web-${QA_CATALOGUE_WEB_VERSION} qa-catalogue \
 && cd qa-catalogue \
 && composer install \
 && mkdir config \
 && echo dir=/opt/qa-catalogue/output > /var/www/html/qa-catalogue/configuration.cnf \
 && echo include=config/configuration.cnf >> /var/www/html/qa-catalogue/configuration.cnf \
 # && cp /var/www/html/qa-catalogue/configuration.js.template /var/www/html/qa-catalogue/configuration.js \
 && touch /var/www/html/qa-catalogue/selected-facets.js \
 && if [ ! -d /var/www/html/qa-catalogue/cache ]; then \
      mkdir /var/www/html/qa-catalogue/cache ; \
    fi \
 && chown www-data:www-data -R /var/www/html/qa-catalogue/cache \
 && chmod g+w -R /var/www/html/qa-catalogue/cache \
 && touch cache/selected-facets.js \
 && if [ ! -d _smarty ]; then \
      mkdir _smarty ; \
    fi \
 && chgrp www-data -R _smarty \
 && chmod g+w -R _smarty \
 && if [ ! -d /var/www/html/qa-catalogue/libs ]; then \
      mkdir /var/www/html/qa-catalogue/libs ; \
    fi \
 && if [ ! -d /var/www/html/qa-catalogue/images ]; then \
        mkdir /var/www/html/qa-catalogue/images ; \
    fi \
 && sed -i.bak 's,</VirtualHost>,        RedirectMatch ^/$ /qa-catalogue/\n        <Directory /var/www/html/qa-catalogue>\n                Options Indexes FollowSymLinks MultiViews\n                AllowOverride All\n                Order allow\,deny\n                allow from all\n                DirectoryIndex index.php index.html\n        </Directory>\n</VirtualHost>,' /etc/apache2/sites-available/000-default.conf \
 && echo "\nWEB_DIR=/var/www/html/qa-catalogue/\n" >> /opt/qa-catalogue/common-variables

# hbz: create symlinks
RUN cd /var/www/html \
 && ln -s qa-catalogue hbz \
 && ln -s qa-catalogue hbz-update

# install Solr
COPY ${SOLR_INSTALL_SOURCE}* /opt

RUN echo "install solr" \
 && cd /opt \
 && MAJOR_VERSION=$(echo $SOLR_VERSION | sed 's/^\([0-9]*\)\..*/\1/') \
 && if [ ${MAJOR_VERSION} -gt 8 ]; then \
      SOLR_PACKAGE="solr-${SOLR_VERSION}.tgz" ; \
      SOLR_DOWNLOAD_PATH="solr/solr" ; \
      SOLR_EXTRACT_METHOD="tgz" ; \
    else \
      SOLR_PACKAGE="solr-${SOLR_VERSION}.zip" ; \
      SOLR_DOWNLOAD_PATH="lucene/solr" ; \
      SOLR_EXTRACT_METHOD="zip" ; \
    fi \
 && if [ ! -f solr-${SOLR_PACKAGE} ]; then \
      DOWNLOAD_URL=https://archive.apache.org/dist/${SOLR_DOWNLOAD_PATH}/${SOLR_VERSION}/${SOLR_PACKAGE} ; \
      curl -L ${DOWNLOAD_URL} --output /opt/${SOLR_PACKAGE} ; \
    fi \
 && if [ "${SOLR_EXTRACT_METHOD}" = "zip" ]; then \
      echo "unzip -q ${SOLR_PACKAGE}" ; \
      unzip -q ${SOLR_PACKAGE} ; \
    elif [ "${SOLR_EXTRACT_METHOD}" = "tgz" ]; then \
      echo "tar -xvzf ${SOLR_PACKAGE}" ; \
      tar -xzf ${SOLR_PACKAGE} ; \
    fi \
 && rm ${SOLR_PACKAGE} \
 && ln -s solr-${SOLR_VERSION} solr

# init process supervisor
RUN echo "install supervisor" \
 && apt-get update \
 && apt-get install -y --no-install-recommends supervisor \
 && mkdir -p /var/log/supervisor \
 && rm -rf /var/lib/apt/lists/*

COPY docker/supervisord.conf /etc/

WORKDIR /opt/qa-catalogue

CMD ["/usr/bin/supervisord", "-c", "/etc/supervisord.conf"]
