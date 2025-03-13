# 3.spring-boot-rest-crud
Employee Management System using Spring Boot, JPA, and MySQL — a full-fledged CRUD application with a layered architecture (Controller, Service, DAO). It supports operations like creating, reading, updating, and deleting single/multiple employees, along with a daily database backup job. Built for clean code, efficiency, and scalability.


----STARTS----

IN THIS WE WILL BE DOING THE CRUD OPERATIONS ON THE EMPLOYEES

1. Create the schema using the mysql queries and given in the MySQL Folder > schema.sql File.

2. Configure the application.properties for db connection

3. Create entity class - "Employee" [for ORM], Data Access Classes (DAO) - EmployeeDAO [It's Abstration - what needs to be done but don't know how to be done - and its impl. that has entitymanager which has jpa implementation.
EmployeeDAO is our own app’s Data Access Object interface — it’s not part of JPA at all.
JPA is the interface/specification — it defines the rules and methods. In our case EntityManager is part of JPA — it’s the main interface JPA provides to interact with the database.
Hibernate (or any other JPA provider) is the implementation — it does the real work of converting Java objects to database records and vice versa.
EntityManager is the JPA interface, and Hibernate provides its implementation behind the scenes
EmployeeDAOJpaImpl class is @Repository and has entitymanager which will intereact with db.

------ REST CRUD ------
//Create
    //Create Single Employee
    //Create Multiple Employees
//Read
    //Read Single Employee
    //Read Multiple Employees
    //Read All Employees
//Update
    //Update Single Employee
    //Update Multiple Employees
//Delete
    //Delete Single Employee
    //Delete Multiple Employees
    //Delete All Employees


4. READ -
- Controller <--->  DAO <---> DB
-In the controller below method reads the list of the employees present in the db
    @GetMapping("/employees")
        public List<Employee> findAll() {
          return employeeDAO.findAll();
        }
-DB execution is done by entitymanager using a TypedQuery as mentioned in EmployeeDAOJpaImpl
-Hit - http://localhost:8080/api/employees
-NOTE - Anti Patterns [what you should never do -]
        i] DON'T INCLUDE ACTIONS IN THE ENDPOINT - The below are wrong because they mention "List", "delete", "add", "update" in the URL of GetMapping.
                /api/employeesList
                /api/deleteEmployee
                /api/addEmployee
                /api/updateEmployee.
        ii] Instead use HTTP methods to assign actions.- GET/POST/PUT/DELETE


5. Now let's create @Service Layer - [@Service, @RestController and @Repository all are the parts of @ComponentScanning]
Controller  <--->  Service <--->  DAO <---> DB
-Create a new package service
-Same as defined in EmployeeDAO - create interface and implementation
-interface EmployeeService
-impl EmployeeServiceImpl
-So what is happening?
    i] DB Communication is only done by DAO classes - entitymanager and typedquery
    ii] Service layer just takes help from DAO classes to get something from DB
    iii] DAO classes give whatever service layer demands from DB.
    iv] So there is one more layer between controller and DAO classes.
-Benefit?
    i] If there are number of DAO's, they all will be communicating with the service layer only.
    ii] No matter how many tables [entities] are present in the DB, Main source of communication is Service Layer.

                             <--->  EmployeeDAO
    Controller <---> Service <--->  SkillsDAO
                             <--->  PayrollDAO

6. @Transactional vs @Transactional(readOnly=true)
        @Transactional                              @Transactional(readOnly = true)
    For read and write operations               Optimized for read-only operations (select queries)
    (insert, update, delete, select)

    Starts a full transaction, commits on       Uses a lightweight transaction, mainly for consistency
    success, and rolls back on
    RuntimeException

    Slightly slower due to write operation      Faster because it skips unnecessary write-related overhead

    Rolls back on unchecked exceptions by       Same rollback behavior, but no data changes to roll back
    default

7. Now let's do CRUD in DAO and also use service layer for @Transactional Management.
-We will do -
    i]Create
        1]Create Single Employee
        2]Create Multiple Employees
    ii]Read
        1]Read Single Employee
        2]Read Multiple Employees
        3]Read All Employees
     iii]Update
         1]Update Single Employee
         2]Update Multiple Employees
     iv]Delete
         1]Delete Single Employee
         2]Delete Multiple Employees
         3]Delete All Employees

-Create these methods inside EmployeeDAO Interface
-Implement them accordingly in EmployeeJpaImplementation.
-Now Copy the same methods of EmployeeDAO Interface into Employee Service Interface
-Implement them in EmployeeServiceImpl but note that this time we will be using "@Transactional" inside the EmployeeServiceImpl
-@Transaction will come on the changes in the DB - create, update, delete
-The @Transactional annotation on these methods indicates that the entire method should be executed within a single database transaction.
-Note - Best practice is to apply transactional boundaries at the service layer.
        It is the service layer responsibility to manage transaction boundaries.
-Make Controller Methods also.
-To test -
    
    A] Read -
        i] Read All
        GET - http://localhost:8080/api/employees

        ii] Read Single
        GET - http://localhost:8080/api/employees/1

        iii] Read Multiple -
        NOTE - it will be @PostMapping and not @GetMapping in controller
        - because user will pass the list of ids from postman which he wants and also @RequestBody
        to rabs the body of the request (like JSON) and maps it to an object.[data will come from db in JSON, If we need to bind it to
        our employee object then we have to use @RequestBody]
        -In postman also we will hit POST and not GETs

        POST - http://localhost:8080/api/employees/readMultiple
        [1, 2, 3] or [1, 4]

        NOTE - THERE ARE 3 WAYS BY WHICH CLIENT CAN GIVE THE INFORMATION - and we will receive it-
        @RequestParam - Grabs query parameters or form data from the URL. Like - GET /api/employees/readMultiple?ids=1,2,3
        @RequestBody - Grabs the body of the request (like JSON) and maps it to an object. Like - as mentioned above
        @PathVariable - Used for identifying specific resources by their unique identifiers. Like - GET /api/employees/5

    B]Create
        i] Create Single
        POST - http://localhost:8080/api/employees
        {
            "firstName": "John",
            "lastName": "Doe",
            "email": "john.doe@example.com"
        }
        Then to see - hit GET on same link
        GET - http://localhost:8080/api/employees

        ii] Create Multiple
        POST - http://localhost:8080/api/employees/batch
        [
            {
                "firstName": "Alice",
                "lastName": "Smith",
                "email": "alice.smith@example.com"
            },
            {
                "firstName": "Bob",
                "lastName": "Brown",
                "email": "bob.brown@example.com"
            }
        ]

    C] Update
        i] Update Single
        PUT - http://localhost:8080/api/employees
        {
            "id":-1,
            "firstName": "mic",
            "lastName": "tyson",
            "email": "mic.tyson@example.com"
        }
        IT WILL GIVE ERROR.
        Change Id to 1 in above json and then hit enter. And to again get - use GET on same link.

        ii] Update Multiple
        PUT - http://localhost:8080/api/employees/batch
        [
          {
            "id": 1,
            "firstName": "John",
            "lastName": "Doe",
            "email": "john.doe@example.com"
          },
          {
            "id": 2,
            "firstName": "Jane",
            "lastName": "Smith",
            "email": "jane.smith@example.com"
          }
        ]

    C] Delete
            i] Delete Single
            DELETE - http://localhost:8080/api/employees/1

            ii] Delete Multiple
            DELETE - http://localhost:8080/api/employees/batch
            [1,2,3]

10. Let's Make a DATABASE Backup Cron Job too - Daily automatically at 8pm.
    -Use @EnableScheduling in main App
    -It will be @Service class - "DatabaseBackupService"
    -Completed


