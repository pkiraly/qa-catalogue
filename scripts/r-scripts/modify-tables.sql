CREATE INDEX "errorId" ON "issue_details" ("errorId");
CREATE INDEX "recordId" ON "issue_details" ("recordId");
CREATE INDEX "id" ON "issue_summary" ("id");
CREATE INDEX "categoryId" ON "issue_summary" ("categoryId");
CREATE INDEX "typeId" ON "issue_summary" ("typeId");

--- create issue_groups to speed up queries
CREATE TABLE issue_groups AS
SELECT categoryId, typeId, s.MarcPath AS path, COUNT(DISTINCT(s.id)) AS variants, COUNT(DISTINCT(d.id)) AS records, SUM(d.instances) AS instances
FROM issue_summary AS s
LEFT JOIN issue_details AS d ON (s.id = d.errorId)
GROUP BY categoryId, typeId, s.MarcPath;

CREATE INDEX "categoryId" ON "issue_groups" ("categoryId");
CREATE INDEX "typeId" ON "issue_groups" ("typeId");
