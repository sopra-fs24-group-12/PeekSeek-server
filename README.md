# PeekSeek
#### SoPra FS24
___
## Project Goal & Motivation
PeekSeek transforms travel planning into a fun, gamified exploration! This interactive game allows players to virtually explore various destinations on Google Maps while competing with friends. Players choose a city they might want to visit and complete multiple quests by finding specific locations in that city, such as landmarks or brunch spots. At the end of the game, all locations are marked on the city map, giving a sense of an itinerary. This makes travel planning more enjoyable and less stressful, ensuring your trip actually happens!

(TBD- OR A MORE ENGAGING INTRO:)
Can you think of a time when you and your friends were planning to travel together but couldn't agree on a destination? Was everyone excited and motivated, yet planning the itinerary seemed like too much hustle? PeekSeek transforms this challenge into a fun, gamified exploration!

PeekSeek is an interactive game where player virtually explore various destinations on Google Maps in an engaging way while competing with friends. Players choose a city they might want to visit and complete multiple quests by finding specific locations in that city, like landmarks or a brunch spot. At the end of the game, all locations are marked on the city map, giving a sense of an itinerary. This makes travel planning more enjoyable and less stressful, ensuring your trip does actually happen!
___
## Technologies
### Development
- Java
- Spring Boot
### Websockets
- STOMP
### Persistence
- JPA
- H2
### Testing
- JUnit
- Mockito
___
## High-level components
//TODO
___
## Launch & Deployment
### Building with Gradle

You can use the local Gradle Wrapper to build the application.

-   macOS: `./gradlew`
-   Linux: `./gradlew`
-   Windows: `./gradlew.bat`

More Information about [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) and [Gradle](https://gradle.org/docs/).

### Build

```bash
./gradlew build
```

### Run

```bash
./gradlew bootRun
```

You can verify that the server is running by visiting `localhost:8080` in your browser.

### Test

```bash
./gradlew test
```

### Development Mode

You can start the backend in development mode, this will automatically trigger a new build and reload the application
once the content of a file has been changed.

Start two terminal windows and run:

`./gradlew build --continuous`

and in the other one:

`./gradlew bootRun`

If you want to avoid running all tests with every change, use the following command instead:

`./gradlew build --continuous -xtest`
___
## Roadmap
- Implement a chat to communicate during the game
- Add a fun play mode
- Add user registration and a user profile page
___
## Authors and acknowledgment
- [Nils Reusch](https://github.com/Arche1ion)
- [Ece Asirim](https://github.com/asirimece)  
- [Youssef Farag](https://github.com/Figo2003)  
- [Georg Emmermann](https://github.com/emmge)  
- [Silvan Schlegel](https://github.com/silvanschlegel)

We thank Marion Andermatt for her guidance as well as all teaching assistants of the module Software Engineering Praktikum at the University of Zurich for their feedback and considerations on our project.
___
## License
This project is licensed under the Apache License 2.0 - see the LICENSE file for details.


