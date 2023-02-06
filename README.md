# jssh

JSSH is a cli tool that manages various ssh connections. 

## Prerequisites
- Java 17

## Compilation
This is compiled into a native executable using GraalVM by running `quarkus build --native` that uses quarkus cli or `./mvnw package -Pnative` using the mvnw command.

## Future Features
- Add hierarchical structure of connections
- Add support for custom private keys
- Add support to create ssh tunnels
- and many more...