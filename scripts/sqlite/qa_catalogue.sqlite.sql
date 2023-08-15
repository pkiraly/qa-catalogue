BEGIN TRANSACTION;

DROP TABLE IF EXISTS issue_summary;
CREATE TABLE IF NOT EXISTS "issue_summary" (
  "id"         INTEGER,
  "MarcPath"   TEXT,
  "categoryId" INTEGER,
  "typeId"     INTEGER,
  "type"       TEXT,
  "message"    TEXT,
  "url"        TEXT,
  "instances"  INTEGER,
  "records"    INTEGER
);

DROP TABLE IF EXISTS issue_details;
CREATE TABLE IF NOT EXISTS "issue_details" (
  "id"         TEXT,
  "errorId"    INTEGER,
  "instances"  INTEGER
);

COMMIT;
