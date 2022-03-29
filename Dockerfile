FROM ubuntu:20.04

LABEL maintainer="Péter Király <pkiraly@gwdg.de>, Ákos Takács <rimelek@rimelek.hu>"

LABEL description="QA catalogue - a metadata quality assessment tool for MARC based library catalogues."

ARG QA_CATALOGUE_VERSION=0.5.0
ARG DEBIAN_FRONTEND=noninteractive
ARG SMARTY_VERSION=3.1.44
ARG SOLR_VERSION=8.11.1

# install R
ENV TZ=Etc/UTC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime \
 && echo $TZ > /etc/timezone

RUN apt-get update \
    # Install add-apt-repository command
 && apt-get install -y --no-install-recommends software-properties-common \
    # add PPA with pre-compiled cran packages
 && add-apt-repository -y ppa:openjdk-r/ppa \
 && add-apt-repository -y ppa:marutter/rrutter3.5 \
# && add-apt-repository -y ppa:marutter/c2d4u3.5 \
 && apt-get install -y --no-install-recommends \
      # install basic OS tools
      apt-utils \
      nano \
      jq \
      curl \
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
      sqlite3 \
      less \
 && rm -rf /var/lib/apt/lists/*

# install metadata-qa-marc
COPY target/metadata-qa-marc-${QA_CATALOGUE_VERSION}-release.zip /opt

RUN cd /opt \
 && unzip metadata-qa-marc-${QA_CATALOGUE_VERSION}-release.zip \
 && rm metadata-qa-marc-${QA_CATALOGUE_VERSION}-release.zip \
 && mv metadata-qa-marc-${QA_CATALOGUE_VERSION} metadata-qa-marc \
 && mv /opt/metadata-qa-marc/setdir.sh.template /opt/metadata-qa-marc/setdir.sh \
 && mkdir -p /opt/metadata-qa-marc/marc \
 && sed -i.bak 's,BASE_INPUT_DIR=your/path,BASE_INPUT_DIR=/opt/metadata-qa-marc/marc,' /opt/metadata-qa-marc/setdir.sh \
 && sed -i.bak 's,BASE_OUTPUT_DIR=your/path,BASE_OUTPUT_DIR=/opt/metadata-qa-marc/marc/_output,' /opt/metadata-qa-marc/setdir.sh \
 # install web application
 && apt-get update \
 && apt-get install -y --no-install-recommends \
      apache2 \
      php \
      php-sqlite3 \
      unzip \
 && rm -rf /var/lib/apt/lists/* \
 && cd /var/www/html/ \
 && curl -s -L https://github.com/pkiraly/metadata-qa-marc-web/archive/refs/heads/main.zip --output master.zip \
# && curl -s -L https://github.com/pkiraly/metadata-qa-marc-web/archive/0.4.zip --output master.zip \
 && unzip -q master.zip \
 && rm master.zip \
# && mv metadata-qa-marc-web-0.4 metadata-qa \
 && mv metadata-qa-marc-web-main metadata-qa \
 && echo dir=/opt/metadata-qa-marc/marc/_output > /var/www/html/metadata-qa/configuration.cnf \
 # && cp /var/www/html/metadata-qa/configuration.js.template /var/www/html/metadata-qa/configuration.js \
 && touch /var/www/html/metadata-qa/selected-facets.js \
 && mkdir /var/www/html/metadata-qa/cache \
 && chown www-data:www-data -R /var/www/html/metadata-qa/cache \
 && chmod g+w -R /var/www/html/metadata-qa/cache \
 && mkdir /var/www/html/metadata-qa/libs \
 && mkdir /var/www/html/metadata-qa/images \
 && cd /var/www/html/metadata-qa/libs/ \
 && curl -s -L https://github.com/smarty-php/smarty/archive/v${SMARTY_VERSION}.zip --output v$SMARTY_VERSION.zip \
 && unzip -q v${SMARTY_VERSION}.zip \
 && rm v${SMARTY_VERSION}.zip \
 && mkdir -p /var/www/html/metadata-qa/libs/_smarty/templates_c \
 && chmod a+w -R /var/www/html/metadata-qa/libs/_smarty/templates_c/ \
 && sed -i.bak 's,</VirtualHost>,        RedirectMatch ^/$ /metadata-qa/\n        <Directory /var/www/html/metadata-qa>\n                Options Indexes FollowSymLinks MultiViews\n                AllowOverride All\n                Order allow\,deny\n                allow from all\n                DirectoryIndex index.php index.html\n        </Directory>\n</VirtualHost>,' /etc/apache2/sites-available/000-default.conf \
 && echo "\nWEB_DIR=/var/www/html/metadata-qa/\n" >> /opt/metadata-qa-marc/common-variables


# install Solr
RUN apt-get update \
 && apt-get install -y --no-install-recommends \
      lsof \
 && apt-get --assume-yes autoremove \
 && rm -rf /var/lib/apt/lists/* \
 && cd /opt \
 && curl -s -L http://archive.apache.org/dist/lucene/solr/${SOLR_VERSION}/solr-${SOLR_VERSION}.zip --output solr-${SOLR_VERSION}.zip \
 && unzip -q solr-${SOLR_VERSION}.zip \
 && rm solr-${SOLR_VERSION}.zip \
 && ln -s solr-${SOLR_VERSION} solr

# init process configuration
RUN apt-get update \
 && apt-get install -y --no-install-recommends supervisor \
 && mkdir -p /var/log/supervisor \
 && rm -rf /var/lib/apt/lists/*

COPY docker/supervisord.conf /etc/

WORKDIR /opt/metadata-qa-marc

CMD ["/usr/bin/supervisord", "-c", "/etc/supervisord.conf"]
