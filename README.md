# Social Media Application

## Description
A robust console-based social media application that implements Object-Oriented Programming (OOP) concepts, leveraging design patterns and using Java best practices. This project simulates the core functionalities of a social media platform, including user management, messaging, groups, pages, and notifications.


## UML Class Diagram
<img width="10592" alt="Social Media Application" src="https://github.com/user-attachments/assets/aef62d6b-6b05-443e-87b2-d26c862a4ef9" />

## Features

### User Functionality
* Registration & Login: Secure password hashing for user authentication.
* Profile Management: Users can edit their profile information.

### Messaging
* Direct Messages: Send and receive messages between users.

### Notifications
* Keep users updated about important events such as new messages, friend requests, follows, or group invites.

### Groups
* Create and manage groups, add members, and interact with group posts.
* Supports privacy options to control who can view and interact with the group.

### Pages
* Users can create and manage Pages for businesses, organizations, or interests.
* Adding details about the page such as description, category, and contact information.
* Publishing posts on the page to interact with followers.

## Security
* Secure password hashing implemented using a custom algorithm to ensure data safety.


## Architectural Design

### Manager Classes
These classes handle the main logic for their specific features. They act as a middle layer between the repositories (which manage data) and the rest of the application. This makes the code easier to understand and change.

### Repository Classes
These classes are in charge of saving and getting data. They hide the details of how the data is stored and make it easier to switch to a different storage method in the future without changing the rest of the code.

### Database Class
This class provides access to the repositories. Its methods only return the required repository when needed, ensuring a clear and simple way to manage the data layer.

## Future Enhancements
* Implement a graphical user interface (GUI).
* Integrate a database.
* Add multimedia support for posts and messages.
* Extend messaging functionality to include group chats.
