#!/usr/bin/env bash
#
# Builds the Scala project. The result is something like
# target/scala-2.10/europeana-qa_2.10-1.0.jar
#

sbt clean compile package

exit 0

