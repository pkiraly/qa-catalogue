#!/usr/bin/env bash

FREQUENCY=$1
STARTING_VERSION=$2

. $(dirname $0)/../../setdir.sh

HISTORICAL=${BASE_OUTPUT_DIR}/_historical/${NAME}
if [[ ! -d ${HISTORICAL} ]]; then
  mkdir -p ${HISTORICAL}
fi

echo $HISTORICAL

CSVS="count.csv issue-total.csv issue-by-category.csv issue-by-type.csv"
for CSV_FILE in CSVS; do
  if [[ -e ${HISTORICAL}/${CSV_FILE} ]]; then
    rm ${HISTORICAL}/${CSV_FILE}
  fi
done

echo "version,total,processed" > ${HISTORICAL}/count.csv
echo "version,type,instances,records" > ${HISTORICAL}/issue-total.csv
echo "version,id,category,instances,records" > ${HISTORICAL}/issue-by-category.csv
echo "version,id,categoryId,category,type,instances,records" > ${HISTORICAL}/issue-by-type.csv
for DIR in $(ls ${HISTORICAL}); do
  if [[ "$DIR" < "$STARTING_VERSION" ]]; then
    continue
  fi
  if [[ -d ${HISTORICAL}/$DIR ]]; then
    if [[ -f ${HISTORICAL}/$DIR/count.csv.gz ]]; then
      gunzip ${HISTORICAL}/$DIR/count.csv.gz
    fi
    if [[ $(head -1 ${HISTORICAL}/$DIR/count.csv) == "total" ]]; then
      tail -n +2 ${HISTORICAL}/$DIR/count.csv | awk '{print $1","$1}' | sed "s;^;$DIR,;" >> ${HISTORICAL}/count.csv
    else
      tail -n +2 ${HISTORICAL}/$DIR/count.csv | sed "s;^;$DIR,;" >> ${HISTORICAL}/count.csv
    fi

    if [[ -f ${HISTORICAL}/$DIR/issue-total.csv.gz ]]; then
      gunzip ${HISTORICAL}/$DIR/issue-total.csv.gz
    fi
    grep -v 'type,' ${HISTORICAL}/$DIR/issue-total.csv | sed "s;^;$DIR,;" >> ${HISTORICAL}/issue-total.csv; 

    if [[ -f ${HISTORICAL}/$DIR/issue-by-category.csv.gz ]]; then
      gunzip ${HISTORICAL}/$DIR/issue-by-category.csv.gz
    fi
    grep -v 'id,category,instances,records' ${HISTORICAL}/$DIR/issue-by-category.csv | sed "s;^;$DIR,;" >> ${HISTORICAL}/issue-by-category.csv ; 
    if [[ -f ${HISTORICAL}/$DIR/issue-by-type.csv.gz ]]; then
      gunzip ${HISTORICAL}/$DIR/issue-by-type.csv.gz
    fi
    grep -v 'id,categoryId,category,type,instances,records' ${HISTORICAL}/$DIR/issue-by-type.csv | sed "s;^;$DIR,;" >> ${HISTORICAL}/issue-by-type.csv ; 
  fi
done

if [[ -f ${HISTORICAL}/history.sqlite ]]; then
  rm ${HISTORICAL}/history.sqlite
fi
sqlite3 ${HISTORICAL}/history.sqlite << EOF
.mode csv
.import ${HISTORICAL}/count.csv count
.import ${HISTORICAL}/issue-total.csv issue_total
.import ${HISTORICAL}/issue-by-category.csv issue_category
.import ${HISTORICAL}/issue-by-type.csv issue_type
EOF

sqlite3 ${HISTORICAL}/history.sqlite << EOF
.headers on
.separator ,
.output ${HISTORICAL}/timeline-by-category.csv
SELECT id, category, version, ROUND((records * 1.0 / processed) * 100, 2) AS percent 
  FROM issue_category 
  JOIN count USING(version) 
  ORDER BY id, version;

.output ${HISTORICAL}/timeline-by-type.csv
SELECT category, type, version, ROUND((records * 1.0 / processed) * 100, 2) AS percent 
  FROM issue_type
  JOIN count USING(version) 
EOF

Rscript $(dirname $0)/timeline.R ${HISTORICAL} $FREQUENCY
ACTUAL_DIR=$(ls -la ${HISTORICAL}/ | grep -P '^l' | tail -1 | awk '{print $NF}')
if [[ "${ACTUAL_DIR:0:2}" == ".." ]]; then
  ACTUAL_DIR=$(realpath ${HISTORICAL}/${ACTUAL_DIR})
fi
echo "copy timeline-by-category.png to ${ACTUAL_DIR}"
cp ${HISTORICAL}/timeline-by-category.png ${ACTUAL_DIR}/img
cp ${HISTORICAL}/timeline-by-type-*.png ${ACTUAL_DIR}/img
