.echo on
.timer on

--- issue_details indices
CREATE INDEX IF NOT EXISTS "errorId" ON "issue_details" ("errorId");
CREATE INDEX IF NOT EXISTS "recordId" ON "issue_details" ("recordId");

UPDATE id_groupid SET groupId = 0 WHERE groupId = "all";
ALTER TABLE id_groupid MODIFY groupId INTEGER;

UPDATE issue_summary SET groupId = 0 WHERE groupId = "all";
ALTER TABLE issue_summary MODIFY groupId INTEGER;

--- issue_summary indices
CREATE INDEX IF NOT EXISTS "groupId" ON "issue_summary" ("groupId");
CREATE INDEX IF NOT EXISTS "id" ON "issue_summary" ("id");
CREATE INDEX IF NOT EXISTS "categoryId" ON "issue_summary" ("categoryId");
CREATE INDEX IF NOT EXISTS "typeId" ON "issue_summary" ("typeId");
CREATE INDEX IF NOT EXISTS "MarcPath" ON "issue_summary" ("MarcPath");
CREATE INDEX IF NOT EXISTS "idx_mix" ON "issue_summary" (groupId, categoryId, typeId, MarcPath);

--- create issue_groups to speed up queries
--- CREATE TABLE issue_groups AS
--- SELECT groupId,
---        categoryId,
---        typeId,
---        s.MarcPath AS path,
---        COUNT(DISTINCT(s.id)) AS variants,
---        COUNT(DISTINCT(d.id)) AS records,
---        SUM(d.instances) AS instances
--- FROM issue_summary AS s
--- LEFT JOIN issue_details AS d ON (s.id = d.errorId)
--- GROUP BY groupId, categoryId, typeId, s.MarcPath;

--- issue_groups indices
--- CREATE INDEX IF NOT EXISTS "groupId" ON "issue_groups" ("groupId");
--- CREATE INDEX IF NOT EXISTS "categoryId" ON "issue_groups" ("categoryId");
--- CREATE INDEX IF NOT EXISTS "typeId" ON "issue_groups" ("typeId");
