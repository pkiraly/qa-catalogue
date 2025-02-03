#!/usr/bin/env bash
# Creates a timeline
# There are two ways of operation
# - incremental: it checks only the most recently created directory
# - overall: it checks all directories
# the mode is decided automatically based on whether or not `history.sqlite` file exists
# If you would like to start over, delete this file.

. $(dirname "$0")/../../setdir.sh

options=$(getopt -o n:f:s:e: --long name:,frequency:,start:,end: -- "$@")
[ $? -eq 0 ] || {
  echo "Incorrect options provided"
  exit 1
}
eval set -- "$options"

NAME=
# NAME=metadata-qa
FREQUENCY=weekly
START=
END=
while true; do
  case "$1" in
    -n|--name) NAME=$2 ; shift;;
    -f|--frequency) FREQUENCY=$2 ; shift;;
    -s|--start) START=$2 ; shift;;
    -e|--end) END=$2 ; shift;;
    --) shift ; break ;;
  esac
  shift
done

echo "$0 name: $NAME, frequency: $FREQUENCY, start: $START, end: $END"

HISTORICAL="$BASE_OUTPUT_DIR/_historical/$NAME"
mkdir -p "$HISTORICAL"

IS_INCREMENTAL=0
if [[ -e "$HISTORICAL/history.sqlite" ]]; then
  IS_INCREMENTAL=1
fi
echo "historical: $HISTORICAL"
echo "is incremental: ${IS_INCREMENTAL}"
if [[ $IS_INCREMENTAL = 0 ]]; then
  echo "IS NOT INCREMENTAL"
else
  echo "IS INCREMENTAL"
fi

echo "deleting old CSV files"
CSVS="count.csv issue-total.csv issue-by-category.csv issue-by-type.csv"
for CSV_FILE in $CSVS; do
  rm -f "$HISTORICAL/$CSV_FILE"
done

echo "initialize CSV files with header"
if [[ "$IS_INCREMENTAL" = 0 ]]; then
  echo "version,total,processed" > "$HISTORICAL/count.csv"
  echo "version,type,instances,records" > "$HISTORICAL/issue-total.csv"
  echo "version,id,category,instances,records" > "$HISTORICAL/issue-by-category.csv"
  echo "version,id,categoryId,category,type,instances,records" > "$HISTORICAL/issue-by-type.csv"
  FILES=$(ls "$HISTORICAL")
else
  FILES=$(ls -lt "$HISTORICAL" | grep "^l" | awk '{print $9}' | head -1)
fi

echo "files: "
echo "$FILES"

for DIR in $FILES; do
  if [[ "$START" != "" && "$DIR" < "$START" ]]; then
    continue
  fi
  if [[ "$END" != "" && "$DIR" > "$END" ]]; then
    continue
  fi

  echo "processing $DIR"

  if [[ -d "$HISTORICAL/$DIR" ]]; then
    if [[ -f "$HISTORICAL/$DIR/count.csv.gz" || -f "$HISTORICAL/$DIR/count.csv" ]]; then
      if [[ -f "$HISTORICAL}/$DIR/count.csv.gz" ]]; then
        gunzip "$HISTORICAL}/$DIR/count.csv.gz"
      fi
      if [[ $(head -1 "$HISTORICAL/$DIR/count.csv") == "total" ]]; then
        tail -n +2 "$HISTORICAL/$DIR/count.csv" | awk '{print $1","$1}' | sed "s;^;$DIR,;" >> "$HISTORICAL/count.csv"
      else
        tail -n +2 "$HISTORICAL}/$DIR/count.csv" | sed "s;^;$DIR,;" >> "$HISTORICAL/count.csv"
      fi

      if [[ -f "$HISTORICAL/$DIR/issue-total.csv.gz" ]]; then          
        gunzip "$HISTORICAL/$DIR/issue-total.csv.gz"
      fi
      grep -v 'type,' "$HISTORICAL/$DIR/issue-total.csv" | sed "s;^;$DIR,;" >> "$HISTORICAL/issue-total.csv"

      if [[ -f "$HISTORICAL/$DIR/issue-by-category.csv.gz" ]]; then
        gunzip "$HISTORICAL/$DIR/issue-by-category.csv.gz"
      fi
      grep -v 'id,category,instances,records' "$HISTORICAL/$DIR/issue-by-category.csv" | sed "s;^;$DIR,;" >> "$HISTORICAL/issue-by-category.csv"
      if [[ -f "$HISTORICAL/$DIR/issue-by-type.csv.gz" ]]; then
        gunzip "$HISTORICAL/$DIR/issue-by-type.csv.gz"
      fi
      grep -v 'id,categoryId,category,type,instances,records' "$HISTORICAL/$DIR/issue-by-type.csv" | sed "s;^;$DIR,;" >> "$HISTORICAL/issue-by-type.csv"
    fi
  fi
done

if [[ "$IS_INCREMENTAL" = 0 && -f "$HISTORICAL/history.sqlite" ]]; then
  rm "$HISTORICAL/history.sqlite"
fi
sqlite3 "$HISTORICAL/history.sqlite" << EOF
.mode csv
.import $HISTORICAL/count.csv count
.import $HISTORICAL/issue-total.csv issue_total
.import $HISTORICAL/issue-by-category.csv issue_category
.import $HISTORICAL/issue-by-type.csv issue_type
EOF

sqlite3 "$HISTORICAL/history.sqlite" << EOF
.headers on
.separator ,
.output $HISTORICAL/timeline-by-category.csv
SELECT id, category, version, ROUND((records * 1.0 / processed) * 100, 2) AS percent 
  FROM issue_category 
  JOIN count USING(version) 
  ORDER BY id, version;

.output $HISTORICAL/timeline-by-type.csv
SELECT category, type, version, ROUND((records * 1.0 / processed) * 100, 2) AS percent 
  FROM issue_type
  JOIN count USING(version) 
EOF

Rscript $(dirname "$0")/timeline.R "$HISTORICAL" "$FREQUENCY"
ACTUAL_DIR=$(ls -la "$HISTORICAL/" | grep -P '^l' | tail -1 | awk '{print $NF}')
if [[ "${ACTUAL_DIR:0:2}" == ".." ]]; then
  ACTUAL_DIR=$(realpath "$HISTORICAL/$ACTUAL_DIR")
fi
echo "copy timeline-by-category.png to $ACTUAL_DIR"
mv "$HISTORICAL"/timeline-by-category.png "$ACTUAL_DIR/img"
mv "$HISTORICAL"/timeline-by-type-*.png "$ACTUAL_DIR/img"
