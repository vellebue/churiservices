create table COUNTRIES(COUNTRY_ID VARCHAR(2) PRIMARY KEY,
                       COUNTRY_NAME VARCHAR(256) NOT NULL);

create table ARTICLES(ARTICLE_ID VARCHAR(20) PRIMARY KEY,
                      DESCRIPTION VARCHAR(256) NOT NULL,
                      COUNTRY_ID VARCHAR(2) NOT NULL REFERENCES COUNTRIES(COUNTRY_ID));

create table PRICING_CONDITIONS(ID INTEGER PRIMARY KEY,
                                ARTICLE_ID VARCHAR(20) NOT NULL REFERENCES ARTICLES(ARTICLE_ID),
                                TYPE VARCHAR(20),
                                SUBTYPE VARCHAR(20),
                                NUM_ORDER INTEGER,
                                VALUE_TYPE VARCHAR(20),
                                VALUE DECIMAL(19,4),
                                UNIQUE(ARTICLE_ID, TYPE, SUBTYPE));

create sequence SEQ_PRICING_CONDITIONS
    increment by 1
    minvalue 0
    maxvalue 999999999
    start with 0
    cycle
    owned by PRICING_CONDITIONS.ID;

create table FORMATS(FORMAT_ID VARCHAR(5) PRIMARY KEY,
                     DESCRIPTION VARCHAR(256) NOT NULL);

create table ARTICLE_FORMATS(ID INTEGER PRIMARY KEY,
                             ARTICLE_ID VARCHAR(20) NOT NULL REFERENCES ARTICLES(ARTICLE_ID),
                             FORMAT_ID VARCHAR(5) NOT NULL REFERENCES FORMATS(FORMAT_ID),
                             DESCRIPTION VARCHAR(256) NOT NULL,
                             EAN_CODE VARCHAR(30),
                             EAN_TYPE VARCHAR(10),
                             MIN_UNIT BOOLEAN NOT NULL,
                             SALE_UNIT BOOLEAN NOT NULL,
                             CONVERSION_FACTOR DECIMAL(8,3) NOT NULL,
                             UNIQUE(ARTICLE_ID, FORMAT_ID));

create sequence SEQ_ARTICLE_FORMATS
    increment by 1
    minvalue 0
    maxvalue 999999999
    start with 0
    cycle
    owned by ARTICLE_FORMATS.ID;