BEGIN TRANSACTION;

-- particular types of errors and how often they have been found
DROP TABLE IF EXISTS issue_summary;
CREATE TABLE IF NOT EXISTS "issue_summary" (
  "id"         INTEGER,  -- identifier of the error
  "MarcPath"   TEXT,     -- the location of the error in the bibliographic record
  "categoryId" INTEGER,  -- the identifier of the category of the error
  "typeId"     INTEGER,  -- the identifier of the type of the error
  "type"       TEXT,     -- the description of the type
  "message"    TEXT,     -- extra contextual information 
  "url"        TEXT,     -- the url of the definition of the data element
  "instances"  INTEGER,  -- the number of instances this error occured
  "records"    INTEGER   -- the number of records this error occured in
);

-- how many instances of an error occur in a particular bibliographic record
DROP TABLE IF EXISTS issue_details;
CREATE TABLE IF NOT EXISTS "issue_details" (
  "id"         TEXT,    -- the record identifier
  "errorId"    INTEGER, -- the error identifier (-> issue_summary.id)
  "instances"  INTEGER  -- the number of instances of an error in the record
);

COMMIT;
