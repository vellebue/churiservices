create table COUNTRIES(COUNTRY_ID VARCHAR(2) PRIMARY KEY,
                       COUNTRY_NAME VARCHAR(256) NOT NULL);

create table REGIONS(COUNTRY_ID VARCHAR(2),
                     REGION_ID VARCHAR(100),
                     REGION_NAME VARCHAR(256),
                     PRIMARY KEY(COUNTRY_ID, REGION_ID));

create table ADDRESSES(ADDRESS_ID INTEGER PRIMARY KEY,
                       ADDRESS VARCHAR(256) NOT NULL,
                       ZIP_CODE VARCHAR(30) NOT NULL,
                       CITY VARCHAR(100) NOT NULL,
                       REGION_ID VARCHAR(100) NOT NULL,
                       COUNTRY_ID VARCHAR(2) NOT NULL);
create sequence SEQ_ADDRESSES
    increment by 1
    minvalue 0
    maxvalue 999999999
    start with 0
    cycle
    owned by ADDRESSES.ADDRESS_ID;

create table ORDER_HEADERS(ORDER_ID INTEGER PRIMARY KEY,
                           CUSTOMER_ORDER_ID VARCHAR(30) NOT NULL,
                           CUSTOMER_ID VARCHAR(20) NULL,
                           DELIVERY_ADDRESS_ID INTEGER NOT NULL REFERENCES ADDRESSES(ADDRESS_ID),
                           INVOICE_ADDRESS_ID INTEGER NOT NULL REFERENCES ADDRESSES(ADDRESS_ID),
                           BASE_VALUE DECIMAL(14,2) NOT NULL DEFAULT 0,
                           VAT_TAX_VALUE DECIMAL(14,2) NOT NULL DEFAULT 0,
                           TOTAL_VALUE DECIMAL(14,2) NOT NULL DEFAULT 0);

create sequence SEQ_ORDERS
    increment by 1
    minvalue 0
    maxvalue 999999999
    start with 0
    cycle
    owned by ORDER_HEADERS.ORDER_ID;

create table ORDER_LINES(ORDER_ID INTEGER,
                         LINE_ID INTEGER,
                         ARTICLE_ID VARCHAR(20) NOT NULL,
                         ARTICLE_DESCRIPTION VARCHAR(256) NOT NULL,
                         NUM_ITEMS INTEGER NOT NULL DEFAULT 0,
                         BASE_PRICE DECIMAL(15,3) NOT NULL DEFAULT 0.0,
                         VAT_TAX DECIMAL(15,3) NOT NULL DEFAULT 0.0,
                         TOTAL_PRICE DECIMAL(15,3) NOT NULL DEFAULT 0.0,
                         TOTAL_VALUE DECIMAL(15,3) NOT NULL DEFAULT 0.0,
                         PRIMARY KEY(ORDER_ID, LINE_ID))