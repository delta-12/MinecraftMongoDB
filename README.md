# MinecraftMongoDB

MinecraftMongoDB is plugin that connects Minecraft Java servers with MongoDB database.

## Setup

1. Download the .jar file from the [latest release](https://github.com/delta-12/MinecraftMongoDB/releases/download/v1.1.0/MinecraftMongoDB-1.1.jar) and place it in your server's `plugins` directory.
2. Start the server.
3. Specify the appropriate values for each key in `config.yml` to allow the plugin to connect to your MongoDB database.
4. Reload the server.

## Example config.yml

```
mongo-client-uri: mongodb+srv://exampleUser:examplePassword@cluster0.example.mongodb.net/
database-name: exampleDB
collection-name: example_minecraft_servers
server-name: Example Minecraft Server
```

Note: A document with a matching name must already exist in the specified collection in your MongoDB database.

