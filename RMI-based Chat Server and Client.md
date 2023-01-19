# RMI-based Chat Server and Client

This is a simple example of a chat server and client built using Java RMI. The server provides methods for registering and unregistering clients, broadcasting messages to all clients, and sending private messages to individual clients. The client can register with the server, send messages to the server and receive message from server.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- [Java SE Development Kit 8](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html) or higher
- [RMI registry](https://docs.oracle.com/en/java/javase/11/docs/specs/rmi/index.html) needs to be running on the server machine

### Server-side

1. Start the RMI registry on the server machine.
2. Compile the ChatServer.java and ChatServerInterface.java files using the command `javac ChatServer.java ChatServerInterface.java`
3. Start the ChatServer by running the command `java ChatServer`

### Client-side

1. Compile the ChatClient.java and ChatClientInterface.java files using the command `javac ChatClient.java ChatClientInterface.java`
2. Start the ChatClient by running the command `java ChatClient`
3. Enter your name when prompted and start sending and receiving messages.

### Usage

1. Register your name with the server
2. Broadcast messages to all clients by entering the message and press 'Send to All' button
3. Send private message to specific client by entering the recipient's name and the message then press 'Send to One' button
4. Logout by entering 'logout' in the message box and press 'Send to All' button
