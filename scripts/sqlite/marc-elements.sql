DROP TABLE IF EXISTS "marc_elements";
CREATE TABLE IF NOT EXISTS "marc_elements" (
  "groupId"             INTEGER,
  "documenttype"        TEXT,
  "path"                TEXT,
  "sortkey"             TEXT,
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
CREATE INDEX IF NOT EXISTS "gme_groupId" ON "marc_elements" ("groupId");
CREATE INDEX IF NOT EXISTS "gme_documenttype" ON "marc_elements" ("documenttype");
CREATE INDEX IF NOT EXISTS "gme_sortkey" ON "marc_elements" ("sortkey");
