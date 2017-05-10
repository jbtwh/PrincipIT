# PrincipIT
Exercise 1: Java Development

Write a JAVA application that fulfils the following requirements. You will have to present your application as well as the source code written for it.


Requirement no.
Req_001
The application shall be written and build in JAVA.

Requirement no.
Req_002
The application shall have a graphical user interface implemented with JavaFX.

Requirement no.
Req_003
The application shall be executable on Microsoft Windows.

Requirement no.
Req_004
The application shall have a function that runs “tasklist” as a console command. The output of this command shall be returned to the application and show the following information (of each task, sorted by used memory) within the graphical user interface:
Name
PID
Used Memory

Requirement no.
Req_005
The application shall provide a manner of handling different OS language settings which might affect the result of the “tasklist” command.





Requirement no.
Req_006
The application shall have a function that removes any duplicates from the listed tasks (assuming the name is the identifier – ignoring the PID). Tasks of the same name shall be grouped together and the used memory aggregated.

Requirement no.
Req_007
The application shall have a function that exports the cleaned list of tasks (see Req_005) into a XML file abiding by the following scheme:

<tasks>
<task>
<name></name>
<memory></memory>
</task>
</tasks>

Requirement no.
Req_008
The application shall utilize a SaveFileDialog form (or something equivalent) to give the user a method to choose the filename and location of the new xml file.

Requirement no.
Req_009
The application shall have a function to re-import the saved XML file and compare the contents to the data shown in the graphical user interface. Any changes shall be shown in the GUI.

Requirement no.
Req_010
The application shall provide a function that exports the gathered data into Microsoft Excel and generate a chart about the memory usage of tasks.












Exercise 2: SQL statements

Premise:
A SQL database consists of the following tables:

Internal Users:
Employee ID
Username
Department
Job title
Location
Phone number
Mail address

External Users:
Employee ID
Username
Location
Phone number

Locations:
Location ID
Location
Facility Manager

Write a single SELECT statement that returns all users (internal and external) with their Employee ID, Username, Department, Location and Job title (where applicable) that work in a location managed by “Location Manager Alpha”.

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

протестировано на h2
