BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "issue_summary" (
  "groupId"    INTEGER,
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
CREATE TABLE IF NOT EXISTS "id_groupid" (
  "id"         TEXT,
  "groupId"    INTEGER
);
CREATE TABLE IF NOT EXISTS "issue_group_types" (
  "groupId"    INTEGER,
  "typeId"     INTEGER,
  "records"    INTEGER,
  "instances"  INTEGER
);
CREATE TABLE IF NOT EXISTS "issue_group_categories" (
  "groupId"    INTEGER,
  "categoryId" INTEGER,
  "records"    INTEGER,
  "instances"  INTEGER
);
CREATE TABLE IF NOT EXISTS "issue_group_paths" (
  "groupId"    INTEGER,
  "typeId"     INTEGER,
  "path"       TEXT,
  "records"    INTEGER,
  "instances"  INTEGER
);
COMMIT;
