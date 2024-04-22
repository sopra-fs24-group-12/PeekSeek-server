
# Contribution History

Record of contributions made to the project and corresponding tasks

---

## Week 1, 2 (Easter Break): [26.03. - 09.04]

### Georg
- Basic file setup
  - [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/822d549c800364a12b50eb4da0f124f95125748d)
- Basic websocket setup
  - [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/21cfb8c3afb6872fb0336a681977beb5a6555526)
- Entity setup for database relations
  - [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/d89a66d8c4649870407565c01184881c1b147ae4)
- Lobby controller and service methods for all main functions
  - [Creating lobby](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/28fe2b268f4fcebc9fde93f145d1975669b21941)
  - [Get all existing lobbies](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/c30c667b1c91733a2d8bcfc5c9ad7c8e06569a4e)
  - [Join lobby](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/b8b473c2bd6af270eebbc75b4917d3804f200770)
  - [Update lobby settings](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/f96f1eef550b617c3a99df46e2369eb44389c9d8)
  - [Get information about specific lobby](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/bdca5239dae7ba3f17f7cdf2215ef59d3097aec7)
  - [Leave lobby](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/8954e0ed533dfd0276d7251d03ff7f75ee848602)
  - [Start game](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/215720e1500dbdc0812f09be584d36daa473e0bf)
- Reworked the persistence to store the game and lobby objects in a HashMap (in the future the game summary will be persisted in a database)
  - [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/bd17d39f84a3921f63fb988c82461f7a97bb3156)
- Game controller and service methods
  - [Add a submission](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/bd17d39f84a3921f63fb988c82461f7a97bb3156)
  - [Timer that runs per round to initiate different phases](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/bd17d39f84a3921f63fb988c82461f7a97bb3156)
  - [Start next round](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/bd17d39f84a3921f63fb988c82461f7a97bb3156)
  - [Get information on current round](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/bd17d39f84a3921f63fb988c82461f7a97bb3156)
  - [Download and store streetview image for each submission](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/dfb103e1a852077605a9a41bff43849aa29b6786)

### Silvan
- [x] Setup NextUI on Client-Template [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-client/commit/0dacd8129b46d9d4e339bc3f94ab6557591cb22a)
- [x] Create UI for Game Submission Rating [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-client/commit/0d180988a6e24d59a4faa991d81fe741a1426054)
- [x] Create UI for Voting Results [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-client/commit/87db439b047ae26792263d5e8a5e18a35ac4ba8a)
- [x] Create UI for Timer [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-client/commit/87db439b047ae26792263d5e8a5e18a35ac4ba8a)
- [x] Create UI for Leaderboard [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-client/commit/ca9cb1bb0a02b245133647f891bfe00adf8138b4)
- [x] Create UI for Submission Card and Winning Card [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-client/commit/0d180988a6e24d59a4faa991d81fe741a1426054)
- [x] Create Basic Skeleton for Chat [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-client/commit/0d180988a6e24d59a4faa991d81fe741a1426054)

### Ece
- Setup Tailwind.css, NextUI, general design and components (base container, etc.).
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/30)
- Setup background image
- Create UI for Landing Page
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/33)
- Create UI for Join Lobby Page
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/15)
- Create UI for Create Lobby Page
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/17)
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/14)
- Create UI for Game Page
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/66)


### Nils
- Added voting, getLeaderboard endpoint
  - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/42)
- Added functionality to determine winningSubmission, calulatePoints, awardPoints
  - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/41)
- Added getSubmissions, getWinningSubmissions endpoint, getLeaderboard, postVoting functionality in GameService
  - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/94)
- Added SubmissionGetDTO and VotingPostDTO (also in DTOMapper)
  - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/20)

### Youssef
- Added Submission
  - [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/00111046ad1aa61645ca1dd6926f6b6ea3759c3c)
- Added SubmissionData
  - [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/a9b50ad612288c799d9683b3ff3fd0f03d22b08c)
- Added functionality to determine all participants sorted by their scores for leaderboard
  - [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/d826d790bb6868abae5a28ce73f058abc643b3af)
- Added get leaderboard as enpoint
  - [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/d826d790bb6868abae5a28ce73f058abc643b3af)
- Addded LeaderboardGetDTO
  - [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/d826d790bb6868abae5a28ce73f058abc643b3af)
- Assigned these issues to myself and worked on them partly, Nils worked also on these issues but did not see that I assigned them to myself, he finished earlier and pushed his part (voting and winingSubmission endpoint) 
  - [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/34ff3eab93cc2bbb22b7757da55cc482ff898a0e)
- Added getLeaderboard, in GameService as well as corresponding endpoint and DTO
  - [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/d826d790bb6868abae5a28ce73f058abc643b3af)
- Added GameGetDTO
  - [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/38e23e74556503eb327e7b820411abf22a34564b)
- Main structure of winningSubmission implemented
  - [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/fc473f71a8748548cc5ada37a1049843c651a285)

  
---

## Week 3: [09.04. - 16.04]

### Georg
- Store the game summary in the database to be able to retrieve it after a game is done
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/122)
- Handle transitions within a round correctly and send websocket messages to all participants
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/127)
- Reset the lobby for re-use so that participants can join an already set up lobby 
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/123)
- Deal with the case when an admin is leaving the lobby
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/124)
- After last round is over, correctly transfer to the game summary and close game
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/126)
- Add authorization mechanism to game service methods
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/128)
- Adjust configuration and test websockets when deployed
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/136)
- Rework persistence for coordinates used in the game
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/137)
- Go through sample use cases and fix bugs 
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/138)
      
### Silvan
- [x] [UI for GameSummary Page](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/18)
- [x] [UI List of Winning Submissions for GameSummary](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/19)
- [x] [Website Background Deployment Fix](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/52)
- [x] [Added Pick and Ban Buttons to Submission Cards](https://github.com/orgs/sopra-fs24-group-12/projects/2/views/1?pane=issue&itemId=56961333)
- [x] [Made Submission Images clickable and added hover effect for UX](https://github.com/orgs/sopra-fs24-group-12/projects/2/views/1?pane=issue&itemId=56961333)

### Ece
- Created UI for Lobby Page
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/14)
- Created input component for quests
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/12)
- Created autocomplete input component for destination
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/13)
 - Implemented logic for players joining lobby
     - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/6)
- Created UI for Join User Page
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/64)

### Nils

- Added functionality to GeoCoding API calls
  - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/100)
  - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/101)
- Stored coordinates etc. reveiced by GeoCoding in DB
  - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/102)
- Implemented functionality for all necessary GoogleMaps API calls in the frontend
  - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/97)
  - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/98)
  - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/99)
  - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/106)
  - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/110)

### Youssef
- Fix getLeaderboard endpoint and its corresponding service and DTO
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/96)
- Understanding testing environment and its functionalities and Setting it up
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/116)
- Adding tests for participant- and lobbyDTOMapper
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/117)
- Add test for LobbyRepository
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/118)
- Add test for GameRepository
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/119)
- Add test for GameToGameGetDTO
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/130)
- Add test for ParticipantToParticipantGetDTO
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/131)
- Add test for GameController
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/114)


---

## Week 4: [16.04. - 23.04]

### Georg

### Nils & Silvan
We worked together on these Issues on one device most of the time.
- [x] [Create Game Page Logic](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/94)
- [x] [Adding Listeners to Simple Map etc, using Google Maps JavaScript API to update current location data](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/95)
- [x] [Handle Google Maps javaScript API Simple Map calls](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/96)
- [x] [Handle Google Maps javaScript API Street View calls](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/97)
- [x] [API calls for submissions](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/98)
- [x] [Design of Google Maps Simple Maps](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/99)
- [x] [Add feature to accept noSubmission](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/100)

### Ece
- Implement Lobby logic (connect with backend)
    - Adapt Lobby UI: [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/53)
    - Joined players list: [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/120)
    - API request for destination: [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/165)
- Add time per round component
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/164)
- Fix re-rendering issue in destination & quest input fields
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/5)
- Implement admin - player logic: disable input fields and buttons
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/166)
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/92)

### Youssef

---

## Week 5: [23.04. - 30.04]

### Georg

### Silvan

### Ece

### Nils

### Youssef

