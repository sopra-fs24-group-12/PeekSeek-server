# PeekSeek
#### SoPra FS24

## Project Goal & Motivation
PeekSeek transforms travel planning into a fun, gamified exploration! This interactive game allows players to virtually explore various destinations on Google Maps while competing with friends. Players choose a city they might want to visit and complete multiple quests by finding specific locations in that city, such as landmarks or brunch spots. At the end of the game, all locations are marked on the city map, giving a sense of an itinerary. This makes travel planning more enjoyable and less stressful, ensuring your trip actually happens!

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


## Launch & Deployment
This project uses the Google Geocoding API which requires a valid API key. When the application is being deployed, the correct API key is set automatically based on a secret set in Github. To set the API key for local development, insert it into the [application.properties](https://github.com/sopra-fs24-group-12/PeekSeek-server/blob/main/src/main/resources/application.properties) file, specifically in line 15 such that the line looks like the following: `api.key=YOUR_KEY` where YOUR_KEY is to be replaced by the actual API key. 
<br />
<br />
Since participants are kicked from both the game and the lobby based on inactivity (meaning not sending a "ping" to the server every few seconds), testing the API endpoints using a program like [Postman](https://www.postman.com/) requires commenting out the lines in both [LobbyService.java](https://github.com/sopra-fs24-group-12/PeekSeek-server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs24/service/LobbyService.java) and [GameService.java](https://github.com/sopra-fs24-group-12/PeekSeek-server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs24/service/GameService.java) that start or stop the inactivity timer. This includes any lines in the format of `stopInactivityTimer(...)` or `startInactivityTimer(...)`.

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


## Roadmap
- Implement a chat to communicate during the game
- Add a fun play mode where it's not about discovering a city but just having fun finding funny things
- Add trivia/fun facts about the city in the waiting page between the rounds

## Authors and acknowledgment
- [Nils Reusch](https://github.com/Arche1ion)
- [Ece Asirim](https://github.com/asirimece)  
- [Youssef Farag](https://github.com/Figo2003)  
- [Georg Emmermann](https://github.com/emmge)  
- [Silvan Schlegel](https://github.com/silvanschlegel)

We thank Marion Andermatt for her guidance as well as all teaching assistants of the module Software Engineering Praktikum at the University of Zurich for their feedback and considerations on our project.

## License
This project is licensed under the MIT License - see the [LICENSE](https://github.com/sopra-fs24-group-12/PeekSeek-server/blob/main/LICENSE) file for details.


