﻿
CREATE TABLE IF NOT EXISTS "AB_CORE"."PROFESSIONAL_TYPE"
(
  ID serial NOT NULL PRIMARY KEY,
  P_TYPE CHARACTER VARYING(30),
  DESCRIPTION CHARACTER VARYING(255)
);

CREATE TABLE IF NOT EXISTS "AB_CORE"."IMAGE"
(
	ID Serial NOT NULL PRIMARY KEY,
	IMAGE_TYPE CHARACTER VARYING(30),
	IMAGE_DATA bytea
);

CREATE TABLE IF NOT EXISTS "AB_CORE"."PROFESSIONAL"
(
	ID Serial NOT NULL PRIMARY KEY,
	TYPE_ID integer references "AB_CORE"."PROFESSIONAL_TYPE"(ID),
	FIRST_NAME CHARACTER VARYING(30),
	LAST_NAME CHARACTER VARYING(50),
	COMPANY_NAME CHARACTER VARYING(50),
	AFFILIATION CHARACTER VARYING(100),
	ADDRESS_ONE CHARACTER VARYING(50),
	ADDRESS_TWO CHARACTER VARYING(50),
	ADDRESS_CITY CHARACTER VARYING(50),
	ADDRESS_POST_CODE CHARACTER VARYING(50),
	WEBSITE_URL  CHARACTER VARYING(200),
	PRIMARY_PHONE	CHARACTER VARYING(20),	
	PHONE_TWO	CHARACTER VARYING(20),
	EMAIL		CHARACTER VARYING(50),
        SKYPE		CHARACTER VARYING(20),        
	DESCRIPTION	TEXT,
	IMAGE_ID integer references "AB_CORE"."IMAGE"(ID)
);

CREATE TABLE IF NOT EXISTS "AB_CORE"."ADVICE_CATAGORY"
(
	ID Serial NOT NULL PRIMARY KEY,
	CATAGORY CHARACTER VARYING(50),
	DESCRIPTION TEXT	
);

--
-- TODO Put a check constraint on when we decide if it is a 1-5 ranking or what
--
CREATE TABLE IF NOT EXISTS "AB_CORE"."PROFESSIONAL_ADVICE_CATAGORY"
(
	PROFESSIONAL_ID integer NOT NULL references "AB_CORE"."PROFESSIONAL"(ID),
	CATAGORY_ID integer NOT NULL references "AB_CORE"."ADVICE_CATAGORY"(ID),
	CATAGORY_RANKING smallint,
	UNIQUE (PROFESSIONAL_ID,CATAGORY_ID)
);



