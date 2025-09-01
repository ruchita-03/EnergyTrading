**Energy Trading Trade Management Application**

This project demonstrates how to build a Java + SQL Server application for managing trades in the Energy Trading Domain. The application allows you to perform CRUD operations (Create, Read, Update, Delete) on trades data using Java and SQL Server.

**📌 Project Overview**

Database: SQL Server Management Studio (SSMS)

IDE: Visual Studio Code (VS Code)

Connection: JDBC with SQL Server driver (.jar)

Purpose: Perform CRUD operations on trades data in the energy trading domain

**🛠️ Database Setup**

Open SQL Server Management Studio (SSMS).

Create a new database:
Switch to the new database:
Create the Trades table:
Verify table creation:

**⚙️ Java Project Setup**

Open Visual Studio Code.

Create a new Java project folder.

Inside the project, create a lib folder.

Place the downloaded SQL Server JDBC driver (.jar) inside the lib folder.

Configure the classpath in VS Code to include the .jar file.

**🔗 JDBC Connection**

Use the following code snippet to connect Java to SQL Server:

String url = "jdbc:sqlserver://localhost:1433;databaseName=EnergyTradingDB;encrypt=true;trustServerCertificate=true;";
String user = "your_username";
String password = "your_password";

Connection con = DriverManager.getConnection(url, user, password);

**📂 CRUD Operations**

The application implements:

Create: Insert new trades

Read: Fetch trade records

Update: Modify trade details

Delete: Remove trades

**🚀 Running the Project**

Start SQL Server and ensure EnergyTradingDB is running.

Compile the Java program in VS Code:
Run the program:


