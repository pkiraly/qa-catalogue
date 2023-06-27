FROM ubuntu:20.04

LABEL maintainer="Péter Király <pkiraly@gwdg.de>, Ákos Takács <rimelek@rimelek.hu>"

LABEL description="QA catalogue - a metadata quality assessment tool for MARC based library catalogues."

ARG QA_CATALOGUE_VERSION=0.7.0-rc2
ARG QA_CATALOGUE_WEB_VERSION=0.7.0-rc2
ARG DEBIAN_FRONTEND=noninteractive
ARG SMARTY_VERSION=3.1.44
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
# && add-apt-repository -y ppa:marutter/rrutter3.5 \
# && add-apt-repository -y ppa:marutter/c2d4u3.5 \
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
 && rm -rf /var/lib/apt/lists/*

# install qa-catalogue
COPY target/qa-catalogue-${QA_CATALOGUE_VERSION}-release.zip /opt

RUN cd /opt \
 && unzip qa-catalogue-${QA_CATALOGUE_VERSION}-release.zip \
 && rm qa-catalogue-${QA_CATALOGUE_VERSION}-release.zip \
 && mv qa-catalogue-${QA_CATALOGUE_VERSION} qa-catalogue \
 && mv /opt/qa-catalogue/setdir.sh.template /opt/qa-catalogue/setdir.sh \
 && mkdir -p /opt/qa-catalogue/marc \
 && sed -i.bak 's,BASE_INPUT_DIR=./input,BASE_INPUT_DIR=/opt/qa-catalogue/marc,' /opt/qa-catalogue/setdir.sh \
 && sed -i.bak 's,BASE_OUTPUT_DIR=./output,BASE_OUTPUT_DIR=/opt/qa-catalogue/marc/_output,' /opt/qa-catalogue/setdir.sh \
 # install web application
 && apt-get update \
 && apt-get install -y --no-install-recommends \
      locales \
      apache2 \
      php \
      php-sqlite3 \
      php-curl \
      php-yaml \
      php-intl \
      unzip \
      composer \
      gettext \
 && locale-gen en_GB.UTF-8 \
 && locale-gen de_DE.UTF-8 \
 && rm -rf /var/lib/apt/lists/* \
 && cd /var/www/html/ \
# && curl -s -L https://github.com/pkiraly/qa-catalogue-web/archive/${QA_CATALOGUE_VERSION}.zip --output master.zip \
 && if [ "${QA_CATALOGUE_WEB_VERSION}" = "main" ]; then \
      curl -s -L https://github.com/pkiraly/qa-catalogue-web/archive/refs/heads/main.zip --output master.zip ; \
    else \
      curl -s -L https://github.com/pkiraly/qa-catalogue-web/archive/refs/tags/v${QA_CATALOGUE_WEB_VERSION}.zip --output master.zip ; \
    fi \
 && ls -la \
 && unzip -q master.zip \
 && rm master.zip \
# && mv qa-catalogue-web-0.4 qa-catalogue \
 && mv qa-catalogue-web-${QA_CATALOGUE_WEB_VERSION} qa-catalogue \
 && cd qa-catalogue \
 && composer install \
 && echo dir=/opt/qa-catalogue/marc/_output > /var/www/html/qa-catalogue/configuration.cnf \
 # && cp /var/www/html/qa-catalogue/configuration.js.template /var/www/html/qa-catalogue/configuration.js \
 && touch /var/www/html/qa-catalogue/selected-facets.js \
 && mkdir /var/www/html/qa-catalogue/cache \
 && chown www-data:www-data -R /var/www/html/qa-catalogue/cache \
 && chmod g+w -R /var/www/html/qa-catalogue/cache \
 && touch cache/selected-facets.js \
 && mkdir _smarty \
 && chgrp www-data -R _smarty \
 && chmod g+w -R _smarty \
 && mkdir /var/www/html/qa-catalogue/libs \
 && mkdir /var/www/html/qa-catalogue/images \
# && cd /var/www/html/qa-catalogue/libs/ \
# && curl -s -L https://github.com/smarty-php/smarty/archive/v${SMARTY_VERSION}.zip --output v$SMARTY_VERSION.zip \
# && unzip -q v${SMARTY_VERSION}.zip \
# && rm v${SMARTY_VERSION}.zip \
# && mkdir -p /var/www/html/qa-catalogue/libs/_smarty/templates_c \
# && chmod a+w -R /var/www/html/qa-catalogue/libs/_smarty/templates_c/ \
 && sed -i.bak 's,</VirtualHost>,        RedirectMatch ^/$ /qa-catalogue/\n        <Directory /var/www/html/qa-catalogue>\n                Options Indexes FollowSymLinks MultiViews\n                AllowOverride All\n                Order allow\,deny\n                allow from all\n                DirectoryIndex index.php index.html\n        </Directory>\n</VirtualHost>,' /etc/apache2/sites-available/000-default.conf \
 && echo "\nWEB_DIR=/var/www/html/qa-catalogue/\n" >> /opt/qa-catalogue/common-variables

# install Solr
RUN echo "install lsof" \
 && apt-get update \
 && apt-get install -y --no-install-recommends \
      lsof \
 && apt-get --assume-yes autoremove \
 && rm -rf /var/lib/apt/lists/*

COPY ${SOLR_INSTALL_SOURCE}* /opt

RUN cd /opt \
 && if [ ! -f solr-${SOLR_VERSION}.zip ]; then \
       curl -L http://archive.apache.org/dist/lucene/solr/${SOLR_VERSION}/solr-${SOLR_VERSION}.zip --output /opt/solr-${SOLR_VERSION}.zip ; \
    fi

RUN echo "unzip solr" \
 && cd /opt \
 && unzip -q solr-${SOLR_VERSION}.zip \
 && rm solr-${SOLR_VERSION}.zip \
 && echo "linking solr" \
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
