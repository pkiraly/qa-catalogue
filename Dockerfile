FROM ubuntu:18.04
MAINTAINER  Péter Király <pkiraly@gwdg.de>

# install Java, R
RUN apt-get update -y
RUN apt-get install apt-utils -y
RUN apt-get install software-properties-common -y

# install Java
RUN add-apt-repository -y ppa:openjdk-r/ppa
# RUN apt-get install openjdk-8-jdk openjdk-8-jre -y
RUN apt-get install openjdk-8-jre -y

# install R
ENV DEBIAN_FRONTEND=noninteractive
# ENV TZ=Etc/UTC
# RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN apt-get install r-base -y
# install R modules
RUN apt-get install libxml2-dev curl openssl libcurl4-openssl-dev libssl-dev -y
RUN Rscript -e 'install.packages("tidyverse", dependencies=TRUE)'
# RUN Rscript -e 'install.packages("grid", dependencies=TRUE)'
RUN Rscript -e 'install.packages("gridExtra", dependencies=TRUE)'
RUN Rscript -e 'install.packages("stringr", dependencies=TRUE)'

# install metadata-qa-marc
RUN mkdir -p /opt/metadata-qa-marc/scripts
RUN mkdir -p /opt/metadata-qa-marc/target
COPY target/metadata-qa-marc-0.4-SNAPSHOT-jar-with-dependencies.jar /opt/metadata-qa-marc/target/metadata-qa-marc-0.4-SNAPSHOT-jar-with-dependencies.jar
COPY scripts/*.* /opt/metadata-qa-marc/scripts/
COPY setdir.sh.template /opt/metadata-qa-marc/setdir.sh
COPY common-variables /opt/metadata-qa-marc/
COPY common-script /opt/metadata-qa-marc/
COPY LICENSE /opt/metadata-qa-marc/
COPY README.md /opt/metadata-qa-marc/

COPY validator /opt/metadata-qa-marc/
COPY prepare-solr /opt/metadata-qa-marc/
COPY index /opt/metadata-qa-marc/
COPY completeness /opt/metadata-qa-marc/
COPY classifications /opt/metadata-qa-marc/
COPY authorities /opt/metadata-qa-marc/
COPY tt-completeness /opt/metadata-qa-marc/
COPY serial-score /opt/metadata-qa-marc/
COPY formatter /opt/metadata-qa-marc/
COPY functional-analysis /opt/metadata-qa-marc/
COPY network-analysis /opt/metadata-qa-marc/

RUN mkdir -p /opt/metadata-qa-marc/marc
RUN sed -i.bak 's,BASE_INPUT_DIR=your/path,BASE_INPUT_DIR=/opt/metadata-qa-marc/marc,' /opt/metadata-qa-marc/setdir.sh
RUN sed -i.bak 's,BASE_OUTPUT_DIR=your/path,BASE_OUTPUT_DIR=/opt/metadata-qa-marc/marc/_output,' /opt/metadata-qa-marc/setdir.sh
