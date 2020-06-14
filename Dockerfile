FROM ubuntu:18.04

LABEL maintainer="Péter Király <pkiraly@gwdg.de>"

# install basic OS tools
RUN apt-get update \
 && apt-get install apt-utils -y \
 && apt-get install software-properties-common nano -y \
 && rm -rf /var/lib/apt/lists/*

# install Java
RUN apt-get update \
 && add-apt-repository -y ppa:openjdk-r/ppa \
 && apt-get install openjdk-8-jre-headless -y \
 && rm -rf /var/lib/apt/lists/*

# install R
ENV DEBIAN_FRONTEND=noninteractive
ENV TZ=Etc/UTC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime \
 && echo $TZ > /etc/timezone

RUN apt-get update \
 && apt-get install r-base r-cran-curl r-cran-openssl r-cran-xml2 -y \
 && rm -rf /var/lib/apt/lists/*

# install R modules
RUN apt-get update \
 && apt-get install libxml2-dev curl openssl libcurl4-openssl-dev libssl-dev -y \
 && rm -rf /var/lib/apt/lists/*

RUN Rscript -e 'install.packages("tidyverse", dependencies=TRUE)'
RUN Rscript -e 'install.packages("grid", dependencies=TRUE)'
RUN Rscript -e 'install.packages("gridExtra", dependencies=TRUE)'
RUN Rscript -e 'install.packages("stringr", dependencies=TRUE)'

# install metadata-qa-marc
RUN mkdir -p /opt/metadata-qa-marc/scripts \
 && mkdir -p /opt/metadata-qa-marc/target

COPY target/metadata-qa-marc-0.4-SNAPSHOT-jar-with-dependencies.jar /opt/metadata-qa-marc/target/
COPY scripts/*.* /opt/metadata-qa-marc/scripts/
COPY setdir.sh.template /opt/metadata-qa-marc/setdir.sh

# copy common scripts
COPY common-variables \
     common-script \
     metadata-qa.sh \
     LICENSE \
     README.md /opt/metadata-qa-marc/

# copy analysis scripts
COPY validator \
     prepare-solr \
     index \
     solr-functions \
     completeness \
     classifications \
     authorities \
     tt-completeness \
     serial-score \
     formatter \
     functional-analysis \
     network-analysis /opt/metadata-qa-marc/

RUN mkdir -p /opt/metadata-qa-marc/marc \
 && sed -i.bak 's,BASE_INPUT_DIR=your/path,BASE_INPUT_DIR=/opt/metadata-qa-marc/marc,' /opt/metadata-qa-marc/setdir.sh \
 && sed -i.bak 's,BASE_OUTPUT_DIR=your/path,BASE_OUTPUT_DIR=/opt/metadata-qa-marc/marc/_output,' /opt/metadata-qa-marc/setdir.sh

# install web application
RUN apt-get update \
 && apt-get install apache2 php wget zip -y \
 && rm -rf /var/lib/apt/lists/* \
 && cd /var/www/html/ \
 && wget https://github.com/pkiraly/metadata-qa-marc-web/archive/master.zip \
 && unzip master.zip \
 && rm master.zip \
 && mv metadata-qa-marc-web-master metadata-qa \
 && echo dir=/opt/metadata-qa-marc/marc/_output > /var/www/html/metadata-qa/configuration.cnf \
 && cp /var/www/html/metadata-qa/configuration.js.template /var/www/html/metadata-qa/configuration.js \
 && touch /var/www/html/metadata-qa/selected-facets.js \
 && mkdir /var/www/html/metadata-qa/cache \
 && mkdir /var/www/html/metadata-qa/libs \
 && cd /var/www/html/metadata-qa/libs/ \
 && wget https://github.com/smarty-php/smarty/archive/v3.1.33.zip \
 && unzip v3.1.33.zip \
 && rm v3.1.33.zip \
 && mkdir -p /var/www/html/metadata-qa/libs/_smarty/templates_c \
 && chmod a+w -R /var/www/html/metadata-qa/libs/_smarty/templates_c/ \
 && sed -i.bak 's,</VirtualHost>,        <Directory /var/www/html/metadata-qa>\n                Options Indexes FollowSymLinks MultiViews\n                AllowOverride All\n                Order allow\,deny\n                allow from all\n                DirectoryIndex index.php index.html\n        </Directory>\n</VirtualHost>,' /etc/apache2/sites-available/000-default.conf \
 && echo "#!/usr/bin/env bash" > /entrypoint.sh \
 && echo "echo \"*** this is the entrypoint ***\"\n" >> /entrypoint.sh \
 && echo "service apache2 start" >> /entrypoint.sh \
 && chmod +x /entrypoint.sh

# install Solr
RUN apt-get update \
 && apt-get install lsof -y \
 && rm -rf /var/lib/apt/lists/* \
 && cd /opt \
 && wget -q http://archive.apache.org/dist/lucene/solr/8.4.1/solr-8.4.1.zip \
 && unzip solr-8.4.1.zip \
 && rm solr-8.4.1.zip \
 && ln -s solr-8.4.1 solr \
 && echo "/opt/solr/bin/solr start -force\n" >> /entrypoint.sh \
 && echo "sleep infinity" >> /entrypoint.sh

RUN rm -rf /var/lib/apt/lists
# ENTRYPOINT ["systemctl"]
# CMD ["status", "apache2.service"]

WORKDIR /opt/metadata-qa-marc

ENTRYPOINT ["/entrypoint.sh"]

