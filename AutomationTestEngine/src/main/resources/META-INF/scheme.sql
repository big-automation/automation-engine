create table ElementInputData (
  id int generated by default as identity (start with 1) primary key,
  dataName varchar(25) not null,
  dataValue varchar(100) not null
);