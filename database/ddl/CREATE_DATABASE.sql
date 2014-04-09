create user advisorbook_owner with password 'advisorbook_ownerdev';

CREATE TABLESPACE ab_default
  OWNER advisorbook_owner
  LOCATION 'C:\\data\\postgres';
COMMENT ON TABLESPACE ab_default
  IS 'Advisor Book Default';

CREATE DATABASE advisorbook
  WITH OWNER = advisorbook_owner
       ENCODING = 'UTF8'
       TABLESPACE = ab_default
       CONNECTION LIMIT = 2;

COMMENT ON DATABASE postgres
  IS 'default administrative connection database';


CREATE SCHEMA "AB_CORE"
  AUTHORIZATION advisorbook_owner;

COMMENT ON SCHEMA "AB_CORE"
  IS 'Advisor Book Core schema';


  