#!/usr/bin/env bash
#
# Builds the Scala project. The result is something like
# target/scala-2.10/europeana-qa_2.10-1.0.jar
#

DEFAULT_VERSION=11
VERSION=$1
echo "v1: '$VERSION'"
if [[ "$VERSION" != "12" && "$VERSION" != "11" ]]; then
  VERSION=$DEFAULT_VERSION
fi
echo "v2: '$VERSION'"

if [[ -e build.sbt ]]; then
  rm build.sbt
fi
ln -s build-2.${VERSION}.tpl build.sbt

sbt clean compile package

exit 0

