# PrincipIT
Exercise 1: Java Development

запуск с мейн метода в me.principit.Starter;

Exercise 2: SQL statements

схема:

CREATE TABLE InternalUsers
	(`employeeid` int, `username` varchar(9), `department` varchar(55), `jobtitle` varchar(55),  `location` varchar(55),  `phonenumber` varchar(55),  `mailaddress` varchar(55))
;

CREATE TABLE ExternalUsers
	(`employeeid` int, `username` varchar(9),  `location` varchar(55),  `phonenumber` varchar(55))
;

CREATE TABLE Locations
	(`locationid` int, `location` varchar(9),  `facilitymanager` varchar(55))
;
    
INSERT INTO InternalUsers
	(`employeeid`, `username`, `department`, `jobtitle`, `location`,  `phonenumber`, `mailaddress`)
VALUES
	(1, 'iuname1', 'dep1', 'jt1', 'loc1', 'pn1', 'ma1'),
	(2, 'iuname2', 'dep2', 'jt2', 'loc2', 'pn2', 'ma2'),
	(3, 'iuname3', 'dep3', 'jt3', 'loc3', 'pn3', 'ma3')
;

INSERT INTO ExternalUsers
	(`employeeid`, `username`, `location`, `phonenumber`)
VALUES
	(1, 'euname1', 'loc1', 'pn1'),
	(2, 'euname2', 'loc2', 'pn2'),
	(3, 'euname3', 'loc3', 'pn3')
;

INSERT INTO Locations
	(`locationid`, `location`, `facilitymanager`)
VALUES
	(1, 'loc1', 'fm1'),
	(2, 'loc2', 'fm2'),
	(3, 'loc3', 'fm3')
;


запрос:

select iu.EMPLOYEEID , iu.USERNAME, iu.DEPARTMENT, iu.LOCATION, iu.JOBTITLE

from locations l

inner join  INTERNALUSERS iu

on iu.location=l.location

where l.location='loc3'

union all

select eu.EMPLOYEEID , eu.USERNAME, NULL, eu.LOCATION, NULL

from locations l

inner join  EXTERNALUSERS eu

on eu.location=l.location

where l.location='loc3'



EMPLOYEEID  	USERNAME  	DEPARTMENT  	LOCATION  	JOBTITLE 

3	iuname3	dep3	loc3	jt3

3	euname3	null	loc3	null
