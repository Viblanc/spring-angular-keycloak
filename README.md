# Spring Boot + Angular + Keycloak

This is a simple application made with Spring Boot and Angular. Authentication is taken care of by Keycloak, a popular, open-source user identity and access manager.

## How to run (in dev mode)
First, you will need an access to the IGDB API. The process is relatively painless and explained [here](https://api-docs.igdb.com/#getting-started). Afterwards, you'll need to set up two environment variables `CLIENT_ID` and `CLIENT_SECRET` before going any further, or setup your IDE to do it for you.

Start by launching the databases and our Keycloak instance managed with Docker by typing at the project root :  
`docker compose up -d`  

Launch the backend in the `backend` directory with the command :  
`./mvnw spring-boot:run`  

Finally, launch the front end in the `frontend` directory by first installing the packages with :  
`pnpm install`  
And launch the application with :  
`pnpm start`

## Demo

https://github.com/Viblanc/spring-angular-keycloak/assets/48864055/08acf2e8-f8d6-4d23-a8f1-84e1bebfe912
