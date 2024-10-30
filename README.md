# Challenge Distribution Centers

This project is a Spring Boot application that manages distribution centers and their orders. It includes RESTful APIs to process orders and retrieve distribution centers based on item IDs.

## Prerequisites

- Java 21 or higher
- Maven 3.6.0 or higher
- MongoDB 4.4.0 or higher
- Docker
- Docker Compose

## Getting Started

### Clone the Repository

```sh
git clone https://github.com/denissouzasantos/challenge-distribution-centers.git
cd challenge-distribution-centersmvn clean installmvn clean install

### Build the Project

mvn clean install

### Run the Application

mvn spring-boot:run

### Run the Tests
mvn test

API Endpoints
Process Order
URL: /order
Method: POST
Request Body:
{
  "items": ["123"]
}
Response:
[
    {
        "id": "672185dc3d00491b7241b521",
        "items": [
            {
                "id": "123",
                "distributionCenters": [
                    "CD1",
                    "CD2",
                    "CD3"
                ]
            }
        ]
    }
]
Get All Orders
URL: /order
Method: GET
Response:
[
    {
        "id": "672185dc3d00491b7241b521",
        "items": [
            {
                "id": "123",
                "distributionCenters": [
                    "CD1",
                    "CD2",
                    "CD3"
                ]
            }
        ]
    }
]
Get Distribution Centers by Item
URL: /distributionCenters
Method: GET
Query Parameter: itemId
Response:
{
  "distributionCenters": ["CD1", "CD2", "CD3"]
}