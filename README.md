# Java Persistence

## Overview
The project is a Java-based application that demonstrates CRUD (Create, Read, Update, Delete) operations on a `Student` entity using Hibernate ORM. The application interacts with a database to manage student information, including their name, age, and major.

## Features
- **Insert**: Add new student records to the database.
- **Update**: Modify existing student records.
- **Delete**: Remove student records from the database.
- **Select**: Retrieve and display student records from the database.

## Prerequisites
- Java Development Kit (JDK) 8 or higher
- Maven
- MySQL or any other relational database
- Hibernate ORM

## Setup and Configuration

1. **Clone the repository**

2. **Configure the Database**
   - Create a database named `studentdb` (or any name of your choice).
   - Update the database configuration in `hibernate.cfg.xml` with your database details.

3. **Build the Project**
   
5. **Run the Application**

## Code Structure
- **'HibernateTest.java'**: Main class containing the logic for handling user input and performing CRUD operations.
- **'Student.java'**: Entity class representing the Student table in the database.
- **'HibernateUtil.java'**: Utility class for configuring and providing Hibernate sessions.

## Common Issues
- **Database Connection Errors**: Ensure that the database is running and the connection details in hibernate.cfg.xml are correct.
- **Transaction Management**: Ensure transactions are properly committed and rolled back in case of errors.
- **Entity Mapping**: Ensure the Student class is correctly mapped and annotated with Hibernate/JPA annotations.

## Contact
For any questions or feedback, please contact leowidj@gmail.com.
