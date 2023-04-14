START TRANSACTION;
CREATE TABLE IF NOT EXISTS issue_summary (
  groupId    VARCHAR(50),
  id         INTEGER COMMENT "error id",
  MarcPath   VARCHAR(50),
  categoryId INTEGER,
  typeId     INTEGER,
  type       VARCHAR(50),
  message    TEXT,
  url        VARCHAR(255),
  instances  INTEGER,
  records    INTEGER
);
CREATE TABLE IF NOT EXISTS issue_details (
  id         VARCHAR(30) COMMENT "record id",
  errorId    INTEGER,
  instances  INTEGER
);
CREATE TABLE IF NOT EXISTS id_groupid (
  id         VARCHAR(30) COMMENT "record id",
  groupId    VARCHAR(50)
);
COMMIT;
