create table COUNTRIES(COUNTRY_ID VARCHAR(2) PRIMARY KEY,
                       COUNTRY_NAME VARCHAR(256) NOT NULL);

create table ARTICLES(ARTICLE_ID VARCHAR(20) PRIMARY KEY,
                      DESCRIPTION VARCHAR(256) NOT NULL,
                      COST_FARE DECIMAL(15,3) NOT NULL,
                      SALE_FARE DECIMAL(15,3) NOT NULL,
                      VAT_TYPE VARCHAR(2) NOT NULL,
                      COUNTRY_ID VARCHAR(2) NOT NULL REFERENCES COUNTRIES(COUNTRY_ID));

create table ARTICLE_FORMATS(ARTICLE_ID VARCHAR(20) NOT NULL REFERENCES ARTICLES(ARTICLE_ID),
                             FORMAT_ID VARCHAR(5),
                             MIN_UNIT BOOLEAN NOT NULL,
                             SALE_UNIT BOOLEAN NOT NULL,
                             CONVERSION_FACTOR DECIMAL(8,3) NOT NULL,
                             PRIMARY KEY (ARTICLE_ID,FORMAT_ID));