UPDATE id_groupid SET groupId = 0 WHERE groupId = "all";
ALTER TABLE id_groupid MODIFY groupId INTEGER;
UPDATE issue_summary SET groupId = 0 WHERE groupId = "all";
ALTER TABLE issue_summary MODIFY groupId INTEGER;

-- issue_details indices

ALTER TABLE issue_details ADD INDEX (errorId);
ALTER TABLE issue_details ADD INDEX (id);

-- issue_summary indices

ALTER TABLE issue_summary ADD INDEX (groupId);
ALTER TABLE issue_summary ADD INDEX (id);
ALTER TABLE issue_summary ADD INDEX (categoryId);
ALTER TABLE issue_summary ADD INDEX (typeId);
ALTER TABLE issue_summary ADD INDEX (MarcPath);
ALTER TABLE issue_summary ADD INDEX (groupId, categoryId, typeId, MarcPath);

-- create issue_groups to speed up queries
CREATE TABLE issue_groups AS
SELECT groupId,
       categoryId,
       typeId,
       s.MarcPath AS path,
       COUNT(DISTINCT(d.id)) AS records,
       SUM(d.instances) AS instances
FROM issue_summary AS s
LEFT JOIN issue_details AS d ON (s.id = d.errorId)
GROUP BY groupId, categoryId, typeId, s.MarcPath;

-- issue_groups indices
ALTER TABLE issue_groups ADD INDEX (groupId);
ALTER TABLE issue_groups ADD INDEX (categoryId);
ALTER TABLE issue_groups ADD INDEX (typeId);
