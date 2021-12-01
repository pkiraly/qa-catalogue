BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "issue_groups" (
	"categoryId"	INT,
	"typeId"	INT,
	"path"	TEXT,
	"variants"	,
	"records"	,
	"instances"	
);
CREATE TABLE IF NOT EXISTS "issue_summary" (
	"id"	INTEGER,
	"MarcPath"	TEXT,
	"categoryId"	INTEGER,
	"typeId"	INTEGER,
	"type"	TEXT,
	"message"	TEXT,
	"url"	TEXT,
	"instances"	INTEGER,
	"records"	INTEGER
);
CREATE TABLE IF NOT EXISTS "issue_details" (
	"id"	TEXT,
	"errorId"	INTEGER,
	"instances"	INTEGER
);
-- CREATE INDEX IF NOT EXISTS "errorId" ON "issue_details" (
-- 	"errorId"
-- );
-- CREATE INDEX IF NOT EXISTS "recordId" ON "issue_details" (
-- 	"recordId"
-- );
-- CREATE INDEX IF NOT EXISTS "id" ON "issue_summary" (
-- 	"id"
-- );
-- CREATE INDEX IF NOT EXISTS "categoryId" ON "issue_summary" (
-- 	"categoryId"
-- );
-- CREATE INDEX IF NOT EXISTS "typeId" ON "issue_summary" (
-- 	"typeId"
-- );
COMMIT;
