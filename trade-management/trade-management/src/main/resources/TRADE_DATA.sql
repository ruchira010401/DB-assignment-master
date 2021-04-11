DROP TABLE IF EXISTS TRADE;

CREATE TABLE TRADE (
  trade_Id VARCHAR PRIMARY KEY,
  version VARCHAR(250) NOT NULL,
  counter_Party_Id VARCHAR(250) NOT NULL,
  book_Id VARCHAR(250) DEFAULT NULL,
  maturity_Date DATE NOT NULL,
  created_Date DATE NOT NULL,
  is_expired VARCHAR NOT NULL);

INSERT INTO TRADE (trade_Id, version, counter_Party_Id,book_Id,maturity_Date,created_Date,is_expired) VALUES
    ('T1', '1', 'CP-1','B1','2021-05-03','2021-04-10','N');