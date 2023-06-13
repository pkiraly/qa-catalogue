BEGIN TRANSACTION;

DROP TABLE IF EXISTS issue_summary;
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

DROP TABLE IF EXISTS issue_details;
CREATE TABLE IF NOT EXISTS "issue_details" (
  "id"         TEXT,
  "errorId"    INTEGER,
  "instances"  INTEGER
);

DROP TABLE IF EXISTS id_groupid;
CREATE TABLE IF NOT EXISTS "id_groupid" (
  "id"         TEXT,
  "groupId"    INTEGER
);

DROP TABLE IF EXISTS issue_grouped_types;
CREATE TABLE issue_grouped_types (
  "groupId"     INTEGER,
  "typeId"      INTEGER,
  "record_nr"   INTEGER,
  "instance_nr" INTEGER
);

DROP TABLE IF EXISTS issue_grouped_categories;
CREATE TABLE issue_grouped_categories (
  "groupId"     INTEGER,
  "categoryId"  INTEGER,
  "record_nr"   INTEGER,
  "instance_nr" INTEGER
);

DROP TABLE IF EXISTS issue_grouped_paths;
CREATE TABLE issue_grouped_paths (
  "groupId"     INTEGER,
  "typeId"      INTEGER,
  "path"        VARCHAR(50),
  "record_nr"   INTEGER,
  "instance_nr" INTEGER
);

COMMIT;
