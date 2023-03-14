BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "issue_summary" (
  "groupId"    TEXT,
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
CREATE TABLE IF NOT EXISTS "issue_details" (
  "id"         TEXT,
  "errorId"    INTEGER,
  "instances"  INTEGER
);
COMMIT;
