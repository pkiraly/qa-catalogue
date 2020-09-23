#!/usr/bin/env bash

OUTPUT_DIR=$1

download() {
  NAME=$1
  if [[ -d ${OUTPUT_DIR}/${NAME}.csv.dir ]]; then
    echo "downloading ${NAME}.csv ..."
    cat ${OUTPUT_DIR}/${NAME}.csv.dir/part-* > ${OUTPUT_DIR}/${NAME}.csv
    rm -rf ${OUTPUT_DIR}/${NAME}.csv.dir
    wc -l ${OUTPUT_DIR}/${NAME}.csv
  fi
}

download network-nodes-indegrees
download network-nodes-indegree-stat
download network-nodes-pagerank
download network-nodes-pagerank-stat
download network-nodes-pagerank-histogram
download network-nodes-components
download network-nodes-components-stat
download network-nodes-components-histogram
download network-nodes-degrees
download network-nodes-degrees-stat
download network-nodes-degrees-histogram
