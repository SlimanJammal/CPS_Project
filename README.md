# Car Parking System (CPS) - OCSF Mediator 

## Project Structure

This project serves as a demonstration of an OCSF-based mediator pattern within a Car Parking System (CPS). The structure encompasses three key modules:

1. **Client**: A user-friendly client application built using JavaFX and OCSF. It allows clients to make parking reservations and cancellations effortlessly. The EventBus, implementing the mediator pattern, facilitates communication between classes, particularly between the `SimpleClient` and `PrimaryController`.

2. **Server**: This module constitutes a basic server constructed using OCSF.

3. **Entities**: A shared module that houses all the relevant entities and data structures for the CPS project.

## Running the Project

To run the CPS project and explore its functionality:

1. Execute `mvn install` in the parent project to install the necessary dependencies.

2. Start the server by using the `exec:java` goal within the server module.

3. Launch the client application by executing the `javafx:run` goal within the client module.

4. Utilize the app to make reservations and witness the parking system in action!

## Project Overview

The Car Parking System is a feature-rich application that caters to the needs of both clients and parking management personnel.

**Client Features**:
- Clients can effortlessly make reservations and cancellations via a user-friendly interface.
- The system provides a designated entrance dock for convenient parking access.

**Management Features**:
- Authorized personnel, including managers and employees, can securely access their accounts using unique IDs and passwords.
- Management capabilities include modifying reservations, viewing parking availability, and overseeing all facets of parking management, including handling special occasions and addressing parking malfunctions.

The entire system is developed using Java and managed through Maven. It operates as a client-server system, utilizing the TCP/IP protocol for efficient communication. The user interface (UI) is crafted using JavaFX and Scene Builder, ensuring an intuitive and visually appealing experience for all users.















