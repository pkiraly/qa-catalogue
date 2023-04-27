sqlite3 ${OUTPUT_DIR}/qa_catalogue.sqlite << EOF
CREATE TABLE IF NOT EXISTS "groupped_marc_elements" (
  "groupId"             INTEGER,
  "documenttype"        TEXT,
  "path"                TEXT,
  "packageid"           INTEGER,
  "package"             TEXT,
  "tag"                 TEXT,
  "subfield"            TEXT,
  "number-of-record"    INTEGER,
  "number-of-instances" INTEGER,
  "min"                 INTEGER,
  "max"                 INTEGER,
  "mean"                REAL,
  "stddev"              REAL,
  "histogram"           TEXT
);
CREATE INDEX IF NOT EXISTS "groupId" ON "groupped_marc_elements" ("groupId");
EOF

tail -n +2 ${OUTPUT_DIR}/completeness-groupped-marc-elements.csv > ${OUTPUT_DIR}/completeness-groupped-marc-elements-noheader.csv

echo "import marc elements"
sqlite3 ${OUTPUT_DIR}/qa_catalogue.sqlite << EOF
.mode csv
.import ${OUTPUT_DIR}/completeness-groupped-marc-elements-noheader.csv groupped_marc_elements
EOF

rm ${OUTPUT_DIR}/completeness-groupped-marc-elements-noheader.csv