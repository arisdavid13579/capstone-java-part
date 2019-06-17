# Credit Card Transactions Management System
#### Java Module for Per Scholas' Data Engineering Training Course Final Project

The Java module of the capstone case study project for the Data Engineering course at Platform by Per Scholas. It is a program to access and update the information on a database containing credit card transactions and customer information. 

The program provides an interface to see transaction details in three different scenarios: A list of transactions at a given zipcode(during a given month and year), the total sales amount of transactions of a given type, and the total sales amount of transactions in a given US state. The interface also allows to see customer information in three different scenarios and also to update a given customer's information.

### Installing and running the program

To run the code a MySQL database with the necessary schema must first be running. The schema and some sample data can be found in the db.sql file included in the repository; please use this file to install the schema on MySQL. The login information for this database must be entered in the "DataBaseConfig.ini" file found inside the java project folder.

To execute the main program, please run the "Main" class inside the "presentationLayer" package/directory. This class displays two choices on the console, one choice for each of the program modules. To access the customer module with all the customer details and to update customer information, press 1. To access the transaction module, press two. Either choice will bring you to a screen with more choices. 

The customer module gives you four choices with the first choice allowing you to see customer details. The second choice allows to update customer details. The third choice prepares a bill for a customer on a given month and year. The final choice shows all the transactions by a given customer between two given dates, it first prompts for a day, then a month then a year for the first date and then again for the second date. All four choices will ask for a customer's social security number and credit card number (this information can be found on the database).

The first choice of the transaction module asks for a zipcode, then a month and then a year. The second choice first gives a list of all available transaction types in the database and then asks to choose one. The third choice asks to input a valid US state.

Running any of the modules will save a CSV file with the output information. This CSV file will be saved inside the corresponding directory inside the "Output" directory. 

#### Valid inputs:
Zipcode numbers are 5-digit-long integers (e.g. 12345).
US state names are the two-letter abbreviation (e.g. NY for New York) or the whole state name (e.g. New York).
Month names are either the whole month name (e.g. January), the 3-letter abbreviation (e.g. Jan), or the month number (e.g. 1 or 01 for January).
A valid year is a 4-digit long integer. Two-digit long numbers are assumed to be in the 2000's (i.e., 15 is in 2015). 
All inputs are case insensitive.