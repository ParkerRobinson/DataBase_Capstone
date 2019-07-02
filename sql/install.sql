drop table if exists PhoneNumber cascade;
drop table if exists RepairRecord cascade;
drop table if exists Car cascade;
drop table if exists Customer cascade;
drop table if exists RepairDescription cascade;
drop table if exists MechCert cascade;
drop table if exists Mechanic cascade;
drop table if exists Certification cascade;

CREATE SEQUENCE Customer_Id;
Create table Customer (
  customer_Id INT DEFAULT nextval('Customer_Id') NOT NULL,
  name VARCHAR(20) NOT NULL, 
  address VARCHAR(50) NOT NULL,
  primary key (customer_Id)
);

Create table Car (
  vin CHAR(17),
  year INT NOT NULL,
  make VARCHAR(20),
  model VARCHAR(10),
  customer_Id INT NOT NULL,
  primary key (vin),
  foreign key (customer_Id) references Customer(customer_Id)
);

Create table PhoneNumber (
  phone_Number BIGINT,
  customer_Id INT NOT NULL,
  primary key (phone_Number),
  foreign key (customer_Id) references Customer(customer_Id)
);

CREATE SEQUENCE mechanic_Id;
Create table Mechanic (
    mechanic_Id INT DEFAULT nextval('Mechanic_Id') NOT NULL,
    name VARCHAR(20),
    years_experience INT,
    hourly_rate REAL,
    primary key (mechanic_Id)
);

Create table Certification (
    name VARCHAR(64) NOT NULL,
    primary key (name)
);

Create table MechCert (
    mechanic_Id INT NOT NULL references Mechanic(mechanic_Id) ON DELETE CASCADE,
    name VARCHAR(64) NOT NULL references Certification(name) ON DELETE CASCADE,
    PRIMARY KEY (mechanic_Id, name)
);

Create table RepairDescription (
    description VARCHAR(256) NOT NULL,
    hours_needed INT,
    name VARCHAR(64) references Certification(name),
    parts_cost REAL NOT NULL,
    primary key (description)
);

CREATE SEQUENCE Record_Id;
Create table RepairRecord (
    record_Id INT DEFAULT nextval('Record_Id') NOT NULL,
    date DATE NOT NULL,
    description VARCHAR(256) NOT NULL references RepairDescription(description) ON DELETE CASCADE,
    mechanic_Id INT NOT NULL references Mechanic(mechanic_Id) ON DELETE CASCADE,
    vin CHAR(17) NOT NULL references Car(vin) ON DELETE CASCADE,
    primary key (record_Id)
);
