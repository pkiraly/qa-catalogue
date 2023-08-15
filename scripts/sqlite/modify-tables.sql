--- issue_details indices
CREATE INDEX IF NOT EXISTS "errorId" ON "issue_details" ("errorId");
CREATE INDEX IF NOT EXISTS "recordId" ON "issue_details" ("recordId");

--- issue_summary indices
CREATE INDEX IF NOT EXISTS "id" ON "issue_summary" ("id");
CREATE INDEX IF NOT EXISTS "categoryId" ON "issue_summary" ("categoryId");
CREATE INDEX IF NOT EXISTS "typeId" ON "issue_summary" ("typeId");

--- create issue_groups to speed up queries
DROP TABLE IF EXISTS issue_groups;
CREATE TABLE issue_groups AS
SELECT categoryId,
       typeId,
       s.MarcPath AS path,
       COUNT(DISTINCT(s.id)) AS variants,
       COUNT(DISTINCT(d.id)) AS records,
       SUM(d.instances) AS instances
FROM issue_summary AS s
LEFT JOIN issue_details AS d ON (s.id = d.errorId)
GROUP BY categoryId, typeId, s.MarcPath;

--- issue_groups indices
CREATE INDEX IF NOT EXISTS "categoryId" ON "issue_groups" ("categoryId");
CREATE INDEX IF NOT EXISTS "typeId" ON "issue_groups" ("typeId");
