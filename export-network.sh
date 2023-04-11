#!/usr/bin/env bash

INPUT_DIR=.
SPARK_OUTPUT=
OUTPUT_DIR=network-analysis

for arg in "$@"
do
  echo $arg
  case $arg in
    "--input")        INPUT_DIR=$2 && shift && shift ;;
    "--spark-output") SPARK_OUTPUT=$2 && shift && shift ;;
    "--output")       OUTPUT_DIR=$2 && shift && shift ;;
  esac
done

echo "INPUT_DIR: $INPUT_DIR
SPARK_OUTPUT: $SPARK_OUTPUT
OUTPUT_DIR: $OUTPUT_DIR
"

mkdir -p $OUTPUT_DIR

while IFS="" read -r p || [ -n "$p" ]
do
  TAG=$(echo $p | sed 's/,.*$//')
  if [[ "$TAG" == "tag" ]]; then
    TAG=all
  fi
  echo $TAG
  
  for TYPE in "components" "degrees" "pagerank" "qlink" "qlinkabs"; do 
    for SUBTYPE in "" "-histogram" "-stat"; do
      if [[ "${TYPE}${SUBTYPE}" != "qlink" && "${TYPE}${SUBTYPE}" != "qlinkabs" ]]; then
        PREFIX=${TAG}-${TYPE}${SUBTYPE}
        echo "PREFIX: $PREFIX"
        cat $SPARK_OUTPUT/network-scores-${PREFIX}.csv.dir/*.csv \
          | sed "s/^/$TAG,/" \
          > $OUTPUT_DIR/network-scores-${PREFIX}.csv
      fi
    done
  done
  for TYPE in "density" "clustering-coefficient"; do 
    PREFIX=${TAG}-${TYPE}
    if [ -d $SPARK_OUTPUT/network-scores-${PREFIX}.csv.dir ]; then
      echo "PREFIX: $PREFIX"
      cat $SPARK_OUTPUT/network-scores-${PREFIX}.csv.dir/*.csv \
        | sed "s/^/$TAG,/" \
        > $OUTPUT_DIR/network-scores-${PREFIX}.csv
    fi
  done
done < $INPUT_DIR/network-by-concepts-tags.csv

# all,id,score

echo "::: SECOND ROUND :::"
while IFS="" read -r p || [ -n "$p" ]
do
  TAG=$(echo $p | sed 's/,.*$//')
  if [[ "$TAG" == "tag" ]]; then
    TAG=all
  fi
  echo $TAG
  for TYPE in "components" "degrees" "pagerank" "qlink" "qlinkabs"; do 
    for SUBTYPE in "" "-histogram" "-stat"; do
      PREFIX=${TYPE}${SUBTYPE}
      if [[ "${TYPE}${SUBTYPE}" != "qlink" && "${TYPE}${SUBTYPE}" != "qlinkabs" ]]; then
        echo "PREFIX: $PREFIX"
        if [[ $TAG == "all" && -e $OUTPUT_DIR/network-scores-${PREFIX}.csv ]]; then
          rm $OUTPUT_DIR/network-scores-${PREFIX}.csv
        fi

        # 1. components
        # 2. degrees
        # 3. pagerank
        # 4. components-histogram.csv
        # 5. network-scores-x-stat.csv
        # 6. degrees-histogram
        # 7. pagerank-histogram
        cat $OUTPUT_DIR/network-scores-$TAG-${PREFIX}.csv \
        | grep -v -P "^\d+,componentId,size$" \
        | sed 's/all,componentId,size/tag,componentId,size/' \
        | grep -v -P "^\d+,id,degree$" \
        | sed 's/all,id,degree/tag,id,degree/' \
        | grep -v -P "^\d+,id,score$" \
        | sed 's/all,id,score/tag,id,score/' \
        | grep -v -P "^\d+,size,count$" \
        | sed 's/all,size,count/tag,size,count/' \
        | grep -v -P "^\d+,statistic,value$" \
        | sed 's/all,statistic,value/tag,statistic,value/' \
        | grep -v -P "^\d+,degree,count$" \
        | sed 's/all,degree,count/tag,degree,count/' \
        | grep -v -P "^\d+,score,count$" \
        | sed 's/all,score,count/tag,score,count/' \
        >> $OUTPUT_DIR/network-scores-${PREFIX}.csv
        rm $OUTPUT_DIR/network-scores-$TAG-${PREFIX}.csv
      fi
    done
  done

  for TYPE in "density" "clustering-coefficient" "triCountGraph-stat"; do 
    # PREFIX=density
    if [[ $TAG == "all" && -e $OUTPUT_DIR/network-scores-${TYPE}.csv ]]; then
      rm $OUTPUT_DIR/network-scores-${TYPE}.csv
    fi
    cat $OUTPUT_DIR/network-scores-$TAG-${TYPE}.csv \
        | grep -v -P "^\d+,records,links,density,avgDegree$" \
        | sed 's/all,records,links,density,avgDegree/tag,records,links,density,avgDegree/' \
        | grep -v -P "^\d+,average-clustering-coefficient$" \
        | sed 's/all,average-clustering-coefficient/tag,average-clustering-coefficient/' \
        >> $OUTPUT_DIR/network-scores-${TYPE}.csv
    rm $OUTPUT_DIR/network-scores-$TAG-${TYPE}.csv
  done
done < $INPUT_DIR/network-by-concepts-tags.csv
