create table COUNTRIES(COUNTRY_ID VARCHAR(2) PRIMARY KEY,
                       COUNTRY_NAME VARCHAR(256) NOT NULL);

create table ARTICLES(ARTICLE_ID VARCHAR(20) PRIMARY KEY,
                      DESCRIPTION VARCHAR(256) NOT NULL,
                      COUNTRY_ID VARCHAR(2) NOT NULL REFERENCES COUNTRIES(COUNTRY_ID));

create table PRICING_CONDITIONS(ARTICLE_ID VARCHAR(20) NOT NULL REFERENCES ARTICLES(ARTICLE_ID),
                                TYPE VARCHAR(20),
                                SUBTYPE VARCHAR(20),
                                NUM_ORDER INTEGER,
                                VALUE_TYPE VARCHAR(20),
                                VALUE DECIMAL(19,4),
                                PRIMARY KEY (ARTICLE_ID, TYPE, SUBTYPE));

create table FORMATS(FORMAT_ID VARCHAR(5) PRIMARY KEY,
                     DESCRIPTION VARCHAR(256) NOT NULL);

create table ARTICLE_FORMATS(ARTICLE_ID VARCHAR(20) NOT NULL REFERENCES ARTICLES(ARTICLE_ID),
                             FORMAT_ID VARCHAR(5) NOT NULL REFERENCES FORMATS(FORMAT_ID),
                             MIN_UNIT BOOLEAN NOT NULL,
                             SALE_UNIT BOOLEAN NOT NULL,
                             CONVERSION_FACTOR DECIMAL(8,3) NOT NULL,
                             PRIMARY KEY (ARTICLE_ID,FORMAT_ID));