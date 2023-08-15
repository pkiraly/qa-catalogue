START TRANSACTION;
CREATE TABLE IF NOT EXISTS issue_summary (
  id         INTEGER,
  MarcPath   VARCHAR(50),
  categoryId INTEGER,
  typeId     INTEGER,
  type       VARCHAR(50),
  message    TEXT,
  url        VARCHAR(256),
  instances  INTEGER,
  records    INTEGER
);
CREATE TABLE IF NOT EXISTS issue_details (
  id         VARCHAR(50),
  errorId    INTEGER,
  instances  INTEGER
);
COMMIT;
