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
### External APIs
- Google Geocoding API
___
## High-level components
For our project PeekSeek we have created nine different entities, most importantly Game, Lobby and Participant. Each entity has its own Data stored in a repository. GeocodingDataRepository for example stores all locations of the different cities. The Controller classes call the Service classes, which manage all the functionalities related to the entity classes. Below we provide a more clear understanding of some important classes: 

### LobbyController ([LobbyController.java](https://github.com/sopra-fs24-group-12/PeekSeek-server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs24/controller/LobbyController.java))
The LobbyController class is a REST controller for managing lobbies in our game application. It utilizes services like LobbyService, WebsocketService, and GameService to handle operations such as creating lobbies, retrieving lobbies and participants, joining and leaving lobbies, updating settings, and starting games. The controller handles HTTP requests as well as the websocket communication between backend anad frontend, and it returns or updates data using DTO (Data Transfer Object) classes for consistent API responses. 

### Lobby ([Lobby.java](https://github.com/sopra-fs24-group-12/PeekSeek-server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs24/entity/Lobby.java))
The Lobby class encapsulates various properties and methods to manage its state and participants. Key attributes include the lobby's ID, name, password, duration of rounds, game location, maximum participants, and a list of quests. It maintains participants with a map and tracks their usernames and activity times. The class provides methods to add and remove participants, reset the lobby, update activity times, and remove inactive participants. It also includes fields and methods for managing the lobby's admin and ensuring password protection when necessary.

### Game ([Game.java](https://github.com/sopra-fs24-group-12/PeekSeek-server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs24/entity/Game.java))
The Game class thus encapsulates all necessary data and behaviors for managing a game session, ensuring smooth handling of rounds (an entity), participants (also an entity), and game state transitions.  The state of the game, participants in a game, and rounds of a game are stored in this entity. It also includes attributes such as an ID, round duration, game location, current round, number of rounds, admin ID, and game status. 

### LobbyService ([LobbyService.java](https://github.com/sopra-fs24-group-12/PeekSeek-server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs24/service/LobbyService.java))
The LobbyService 'serves' the lifecycle and operations of game lobbies. It can
create new lobbies, setting initial parameters like name, password, and initializing inactivity timers.
Furthermore, it manages participants within lobbies, including joining, leaving, and updating their activity status. It ensures participants have the necessary permissions and updates their status to keep the lobby active, while monitoring participant's activity and removes inactive participants to keep the lobby functional.
Moreover, it integrates with GeoCodingDataRepository to fetch or save geographical data related to the game locations, validate and save location data.
One important aspect is that it ensures that only authorized participants or admins can perform certain actions, maintaining security and integrity within the lobbies.
By combining these functionalities, LobbyService ensures smooth management and operation of game lobbies, maintaining participant engagement and proper configuration of game settings.

### Websockets
We use websockets to ensure synchronization between all participants. They are used to broadcast events happening in the lobby and during the game. This includes participants joining or leaving a lobby/game, lobby settings being updated, game starting or transferring to the next phase within a game. Additionally, every second a message with the remaining time for each phase of the game is being sent to the participants. This avoids inconsistencies and further contributes to a synchronized experience.

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


