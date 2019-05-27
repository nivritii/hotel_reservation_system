# HRS
Hotel Reservation System

## 1. Technology Stack
- Spring Boot Framework
- Java 8
- My Sql 8.0

## 2. To Run the Program
1. Import the project as existing Maven Spring Boot project into IDE from hotel_reservation_system folder
2. Install JDK 1.8
3. Install MySQL 8.0
    - run at localhost:3306 
    - create databases with name 'hrs'
    - create tables by running Database_Creation sql file
	- seed databases with inital values from Seed_Datavase sql file
	
## 3. Project Scope
1. Content Management System backend
	- Admin can list/create/edit/delete rooms
2. API created to,
	- list all the available rooms for any particular day in the future
	- to book multiple rooms for a number of days
	- to cancel any booking
3. Bonus functionalities included are,
	- API tests written for all the controller classes (API calls)
	- Admin can be seeded
	- Customer can signup
	- Customer can edit any booking before the start date
	- API to list all the available rooms for any particular timeframe in the future
4. Exceptions written to validate,
		- If the specified date has already passed
		- If the available rooms are not enough
		- If start date is after the end date
		- If the room type could not be found
	
## 4. API Documentation
This section describes the available API endpoints with example request and response

### 1. Room Type

#### a. Get available room types

```
GET /room-type/available?start=2099-12-31&end=2100-01-03
```
```
Response:
[
    {
        "id": 1,
        "type": "suite",
        "description": "suite for couple",
        "image": null,
        "quantity": 100,
        "price": 1000
    },
    {
        "id": 2,
        "type": "executive",
        "description": "executive room for one person",
        "image": null,
        "quantity": 200,
        "price": 800
    }
]
```
### 2. Room Type (admin)

#### a. Create new room type

```
POST /admin/room-type/create
Request body:
{
		"type": "balcony",
        "description": "room with seaside view",
        "image": "",
        "quantity": 50,
        "price": 500
}
```
```
Response:
{
    "id": 3,
    "type": "balcony",
    "description": "room with seaside view",
    "image": "",
    "quantity": 50,
    "price": 500
}
```

#### b. Update room type by id
```
PATCH /admin/room-type/3
Request body:
{
		"type": "room with balcony",
        "description": "family room for 6",
        "image": "",
        "quantity": 20,
        "price": 500
}
```
```
Response:
{
    "id": 3,
    "type": "room with balcony",
    "description": "family room for 6",
    "image": "",
    "quantity": 20,
    "price": 500
}
```

#### c. Delete room type by id
```
DELETE /admin/room-type/3
```

#### d. Get room type by id
```
GET /admin/room-type/1
```
```
Response:
{
    "id": 1,
    "type": "suite",
    "description": "suite for couple",
    "image": null,
    "quantity": 100,
    "price": 1000
}
```

#### e. Get all room types
```
GET admin/room-type/all
```
```
[
    {
        "id": 1,
        "type": "suite",
        "description": "suite for couple",
        "image": null,
        "quantity": 100,
        "price": 1000
    },
    {
        "id": 2,
        "type": "executive",
        "description": "executive room for one person",
        "image": null,
        "quantity": 200,
        "price": 800
    }
]
```

### 3. Customer

#### a. Create new customer
```
POST customer/create
Request body:
{
	"username": "customer2",
    "password": "password",
    "name": "customer2"
}
```
```
Response body:
{
    "id": 2,
    "username": "customer2",
    "password": "password",
    "name": "customer2"
}
```

#### b. Update customer by id
```
PATCH /customer/2/update
Request body:
{
		"username": "customer2",
        "password": "test",
        "name": "test"
}
```
```
Response body:
{
    "id": 2,
    "username": "customer2",
    "password": "test",
    "name": "test"
}
```

#### c. Delete customer by id
```
DELETE customer/2/delete
```

#### d. Get customer by id
```
GET /customer/1
```
```
Response body:
{
    "id": 1,
    "username": "customer",
    "password": "customer",
    "name": "password"
}
```

#### e. Get customer by username
```
GET /customer/username/customer
```
```
Response body:
{
    "id": 1,
    "username": "customer",
    "password": "customer",
    "name": "password"
}
```

#### f. Get all customers
```
GET /customer/all
```
```
Response body:
[
    {
        "id": 1,
        "username": "customer",
        "password": "customer",
        "name": "password"
    }
]
```

### 4. Reservation

#### a. Create new reservation
```
POST /reservation/create
Request body:
{
    "roomTypeId": 1,
    "customerId": 1,
    "quantity": 5,
    "startDate": "2019-11-28",
    "endDate": "2019-11-30"
}
```
```
Response body:
{
    "id": 1,
    "roomTypeId": 1,
    "customerId": 1,
    "quantity": 5,
    "startDate": "2019-11-28",
    "endDate": "2019-11-30",
    "cancelled": false,
    "createdAt": "2019-05-21T12:04:44.453",
    "updatedAt": "2019-05-21T12:04:44.453"
}
```

#### b. Get reservation by id
```
GET /reservation/1
```
```
Response body:
{
    "id": 1,
    "roomTypeId": 1,
    "customerId": 1,
    "quantity": 5,
    "startDate": "2019-11-28",
    "endDate": "2019-11-30",
    "cancelled": false,
    "createdAt": "2019-05-21T12:04:44",
    "updatedAt": "2019-05-21T12:04:44"
}
```

#### c. Get all reservations
```
GET /reservation/all
```
```
Response body:
[
{
    "id": 1,
    "roomTypeId": 1,
    "customerId": 1,
    "quantity": 5,
    "startDate": "2019-11-28",
    "endDate": "2019-11-30",
    "cancelled": false,
    "createdAt": "2019-05-21T12:04:44",
    "updatedAt": "2019-05-21T12:04:44"
}
]
```

#### d. Update reservation by id
```
PATCH reservation/1/update
Request body:
{
    "roomTypeId": 1,
    "customerId": 1,
    "quantity": 10,
    "startDate": "2100-01-01",
    "endDate": "2100-01-04"
}
```
```
Response body:
{
    "id": 1,
    "roomTypeId": 1,
    "customerId": 1,
    "quantity": 10,
    "startDate": "2100-01-01",
    "endDate": "2100-01-04",
    "cancelled": false,
    "createdAt": "2019-05-21T12:04:44",
    "updatedAt": "2019-05-21T12:11:45.404"
}
```

#### e. Cancel reservation by id
```
PATCH /reservation/1/cancel
```
```
Response body:
{
    "id": 1,
    "roomTypeId": 1,
    "customerId": 1,
    "quantity": 10,
    "startDate": "2100-01-01",
    "endDate": "2100-01-04",
    "cancelled": true,
    "createdAt": "2019-05-21T12:04:44",
    "updatedAt": "2019-05-21T12:13:38.587"
}
```

### 5. API testing
API test case are written for all the controller classes checking the API calls and for the verification of the exceptions thrown.

#### a. RoomTypeFunctionalTest
in project folder(.\hotel_reservation_system\src\test\java\com\hotel\booking\roomtype)

Test Cases
	- testFindAvailable_Success
	- testFindAvailable_SomeRoomTypesAlreadyReserved
	- testFindAvailable_StartDateAfterEndDate
	- testFindAvailable_DateSpecifiedAlreadyPassed

Test Result
![ALT](./TestCaseResults/RoomTypeFuncationalTestResults.png)

#### b. CustomerFunctionalTest
in project folder (.\hotel_reservation_system\src\test\java\com\hotel\booking\customer)

Test Cases
	- testCreate_Success
	- testCreate_UserNameNotUnique
	- testUpdate_Success
	- testDelete_Success

Test Result
![ALT](./TestCaseResults/CustomerFuncationalTestResults.png)

#### c. ReservationFunctionalTest
in project folder(.\hotel_reservation_system\src\test\java\com\hotel\booking\reservation)

Test Cases
	- testCreate_Success
	- testCreate_StartDateAfterEndDate
	- testCreate_DateSpecifiedAlreadyPassed
	- testCreate_AvailableRoomsNotEnough
	- testFind_Success
	- testFind_NotFound
	- testUpdate_Success
	- testUpdate_StartDateAfterEndDate
	- testUpdate_DateSpecifiedAlreadyPassed
	- testUpdate_AvailableRoomsNotEnough
	- testUpdate_NotFound
	- testUpdate_RoomTypeNotFound
	- testCancel_Success
	- testCancel_NotFound

Test Result
![ALT](./TestCaseResults/ReservationFuncationalTestResults.png)
