Insert into Customer (name, address)
Values ('Ben', '1234 Main St'), ('Brannon', '2345 Washington Ave'), ('Parker', '3456 Stanger St'), ('Jake', '4567 Drillfield Dr'), ('Craig', '5678 Beamer Way'), ('Jack', '6789 Prices Fork'), ('Matt', '7890 Alumni Dr'), ('Jason', '2468 West Campus Dr'), ('Colton', '1357 Main St'), ('Diego', '4680 Main St');

Insert into Car (vin, make, model, year, customer_Id)
Values ('TRUWT28N011046197', 'Honda', 'Accord', 2000, 1), ('2CNBJ1365W6902635', 'Jeep', 'Wrangler', 2001, 2), ('1XPWDBTX48D766660', 'Jeep', 'Cherokee', 1999, 3), ('SAJGX2040XC042591', 'Honda', 'Pilot', 2010, 4), ('JT2BG22K6W0242999', 'Honda', 'Odyssee', 2003, 5), ('3GNFK16318G269795', 'Toyota', 'Prius', 2007, 6), ('JH4DA9380PS016488', 'Toyota', 'RAV4', 2008, 7), ('3B7HC13Z7TG112627', 'Subaru', 'Forester', 2012, 8), ('JH4DB7550RS000461', 'Chevy', 'Silverado', 2000, 9), ('JH4NA1261RT000013', 'Ford', 'F150', 2018, 10);

Insert into PhoneNumber (phone_Number, customer_Id)
Values (4049095240, 1), (2022761740, 2), (7578085041, 3), (8573014041, 4), (404909533, 5), (8089095241, 6), (6789998212, 7), (8043600375, 8), (8043858533, 9), (7612234318, 10);

Insert into Mechanic (name, years_experience, hourly_rate)
Values ('Ben', 30, 30), ('Brannon', 20, 25), ('Parker', 10, 19), ('Jake', 30, 29), ('Brad', 20, 24), ('Jeff', 10, 19), ('John', 30, 29), ('Jack', 20, 24), ('Craig', 10, 18), ('Michael', 30, 28);

Insert into Certification (name)
Values ('A1 Engine Repair'), 
  ('A2 Automatic Transmission'), 
  ('A2 Automatic Transaxle'), 
  ('A3 Manual Drive Train and Axles'), 
  ('A4 Suspension'), 
  ('A4 Steering'), 
  ('A5 Brakes'), 
  ('A6 Electrical/Electronic Systems'), 
  ('A7 Heating and Air Conditioning'), 
  ('A8 Engine Performance');

Insert into MechCert (mechanic_Id, name)
Values (1, 'A1 Engine Repair'), 
  (1, 'A2 Automatic Transmission'), 
  (2, 'A2 Automatic Transaxle'), 
  (2, 'A3 Manual Drive Train and Axles'), 
  (3, 'A4 Suspension'), 
  (4, 'A4 Suspension'), (5, 'A5 Brakes'), 
  (6, 'A6 Electrical/Electronic Systems'), 
  (7, 'A7 Heating and Air Conditioning'), 
  (8, 'A8 Engine Performance');

Insert into RepairDescription (description, hours_needed, name, parts_cost)
Values ('Tail Light Out', 1, NULL, 5), 
  ('Oil Leak', 1, NULL, 20), 
  ('Flat Tire', 1, NULL, 350), 
  ('Transmission Repair', 4, 'A2 Automatic Transmission', 3000), 
  ('Mirror Shattered', 1, NULL, 70), 
  ('Replace Break Pads', 2, 'A5 Brakes', 250), 
  ('Windshield Crack', 2, NULL, 250), 
  ('Engine Install', 10, 'A1 Engine Repair', 10000), 
  ('A/C Repair', 3, 'A7 Heating and Air Conditioning', 175), 
  ('Nitrous Install', 4, 'A8 Engine Performance', 3);

Insert into RepairRecord (date, description, mechanic_Id, vin)
Values ('2010-04-10', 'Tail Light Out', 1, 'TRUWT28N011046197'), 
  ('2010-04-11', 'Oil Leak', 2, '2CNBJ1365W6902635'), 
  ('2010-04-12', 'Oil Leak', 2, '2CNBJ1365W6902635'), 
  ('2010-04-13', 'Tail Light Out', 1, 'SAJGX2040XC042591'), 
  ('2010-04-14', 'Flat Tire', 3, 'JT2BG22K6W0242999'), 
  ('2010-04-15', 'Oil Leak', 2, '3GNFK16318G269795'), 
  ('2010-04-16', 'Flat Tire', 3, 'JH4DA9380PS016488'), 
  ('2010-04-17', 'Mirror Shattered', 1, '3B7HC13Z7TG112627'), 
  ('2010-04-18', 'Tail Light Out', 1, 'JH4DB7550RS000461'), 
  ('2010-04-19', 'Flat Tire', 3, 'JH4NA1261RT000013');