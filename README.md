# CPS Project

OCSF Mediator Example
Structure
Pay attention to the three modules:

client - a simple client built using JavaFX and OCSF. We use EventBus (which implements the mediator pattern) in order to pass events between classes (in this case: between SimpleClient and PrimaryController.
server - a simple server built using OCSF.
entities - a shared module where all the entities of the project live.
Running
Run Maven install in the parent project.
Run the server using the exec:java goal in the server module.
Run the client using the javafx:run goal in the client module.
Press the button and see what happens!
