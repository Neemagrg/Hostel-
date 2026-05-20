# SmartHostel Management System

SmartHostel is a Java web application built with JSP, Servlets, Maven, and MySQL.

## Project Structure

- `src/main/java/com/smarthostel/controller` - Servlet controllers (request handling and routing)
- `src/main/java/com/smarthostel/model` - Data models
- `src/main/java/com/smarthostel/util` - Utility classes (for example, database connection)
- `src/main/webapp` - Frontend views and static assets
- `src/main/webapp/static/css/style.css` - Shared frontend styling
- `src/main/webapp/*.jsp` - Frontend pages (login, register, dashboard, management screens)
- `sql/setup.sql` - Database schema and sample seed data

## Where Is The Frontend?

The frontend is in:

- `src/main/webapp/*.jsp` (UI pages)
- `src/main/webapp/static/css/style.css` (styles)

Main entry page:

- `login.jsp` (configured in `WEB-INF/web.xml` as the welcome page)

## Prerequisites

- JDK 8+ installed
- Maven installed
- MySQL running locally
- Apache Tomcat 11 (Servlet 6.0 / Jakarta EE 10 compatible)

## Setup Database

1. Create schema and tables:
   - Run `sql/setup.sql` in MySQL.
2. Confirm default admin account:
   - Email: `admin@hostel.com`
   - Password: `admin123`

## Configure Database Connection

The app reads database settings from environment variables (with safe defaults):

- `DB_URL` (default: `jdbc:mysql://localhost:3306/smarthostel?useSSL=false&allowPublicKeyRetrieval=true`)
- `DB_USER` (default: `root`)
- `DB_PASSWORD` (default: empty)

PowerShell example:

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/smarthostel?useSSL=false&allowPublicKeyRetrieval=true"
$env:DB_USER="root"
$env:DB_PASSWORD="your_mysql_password"
```

## Build The Application

If Maven shows a JAVA_HOME error, set JAVA_HOME first:

```powershell
$env:JAVA_HOME="C:\Path\To\Your\JDK"
$env:Path="$env:JAVA_HOME\bin;$env:Path"
```

Then build:

```powershell
mvn clean package
```

This creates `target/HostelManagement.war`.

## Run With Tomcat

1. Copy `target/HostelManagement.war` to Tomcat's `webapps` folder.
2. Start Tomcat.
3. Open:
   - `http://localhost:8080/HostelManagement/`

## Notes

- This project currently stores plain passwords for demo purposes. For production, use password hashing and stronger security practices.
- A failed DB connection now throws a clear runtime message to simplify troubleshooting.
