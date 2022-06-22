# SOAPbackendAPI
## Made by flawden

It is a service for interacting with users and their roles. It uses the SOAP communication protocol as the basis.

____

# Table of contents

1. [Features](#Features)
2. [Getting started](#Getting-started)
   1. [Launch via Intellij idea](#Launch-via-Intellij-idea)
   2. [Building a project with Maven](#Building-a-project-with-Maven)
3. [Application Interaction](#Application-Interaction)

## Features

- Getting a specific user with his roles by login
- Getting a list of users
- Adding a user with a list of roles.
- Editing the user and his roles
- Deleting a user.
- Validation when editing and registering a user with a list of errors in the form of an array
- Implementation with Spring Data JPA and Spring web services

To use this application, you can use

1. intellij idea (Running from development environment is easy)
2. Building with Maven
____

# Getting started


## Launch via Intellij idea

Launching through the intellij ide development environment is not a problem. It's worth noting that running through other development environments like Eclipse is not covered here, but running on them is not much different.

First you need to get the project into your development environment in any way. You can use the following command in the Idea terminal:

> gh repo clone Flawden/SOAPbackendAPI

You can also click on the green "Code" button on the repository page and download the project there using the "Download ZIP" button

> [SOAP-backend-API](https://github.com/Flawden/SOAPbackendAPI)

In the environment, you need the Maven plugin to collect the necessary resources, to do this, go to the following path

> Maven -> SOAP-backend-API -> Lifecycle -> Install

Once installed, you can run the project using the tab at the top of the environment:

> Run -> Run 'SoapBackendApiApplication'

or using the green triangle on the Quick Access Toolbar.

## Building a project with Maven

First, make sure that Maven is installed and registered in the system variables, otherwise follow the steps described in this guide: https://csharpcoderr.com/5265/

Next, you need to open the console, in it go to the root of the project using the command

> cd <Path_to_Project>

Then enter the following command

> mvn install

A "Target" folder will appear in the root of the project. Log into it via the console. The folder will contain a .jar file

Run it with the command:

> java -jar <Full_name_of_jar_file>
____

# Application Interaction

Since the application uses the SOAP protocol, you need to send SOAP requests in any way possible (SoapUI is recommended for verification).

By running the application, you can access the WSDL file using the link:

> http://localhost:8080/soap/user.wsdl

This service has the following methods for interaction

| Method | Description |
| ------ | ------ |
| registerUser | Registers a user |
| getUser | Returns a user by login and password with a list of his roles |
| getAllUsers | Returns a list of all users |
| editUser | Edits the user by the current login |
| deleteUser | Deletes a user by login |
| Success | The form is returned as a result of the "editUser", "registerUser", and "deleteUser" methods. Contains possible errors and execution result |