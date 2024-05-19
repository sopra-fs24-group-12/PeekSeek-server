
# Contribution History

Record of contributions made to the project and corresponding tasks

---

## Week 1, 2 (Easter Break): [26.03. - 09.04]

### Georg
- Basic file setup
  - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/182
- Basic websocket setup
  - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/183
- Entity setup for database relations
  - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/184
- Lobby controller and service methods for all main functions
  - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/185
  - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/187
  - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/186
  - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/188
  - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/189
  - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/190
  - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/191
- Reworked the persistence to store the game and lobby objects in a HashMap (in the future the game summary will be persisted in a database)
  - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/192
- Game controller and service methods
  - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/193
  - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/194
  - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/195
  - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/196

### Silvan
- [x] [Setup NextUI on Client-Template](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/30)
- [x] [Create UI for Game Submission Rating](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/39)
- [x] [Create UI for Voting Results with all UI elements](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/40)
- [x] [Create UI for Timer](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/55)
- [x] [Create UI for Leaderboard](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/40)
- [x] [Create UI for Submission Card](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/39)
- [x] [Create Basic Skeleton for Chat](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/4)

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
- [x] [Added voting, getLeaderboard endpoint](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/42)
- [x] [Added functionality to determine winningSubmission, calulatePoints, awardPoints](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/41)
- [x] [Added getSubmissions, getWinningSubmissions endpoint, getLeaderboard, postVoting functionality in GameService](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/94)
- [x] [Added SubmissionGetDTO and VotingPostDTO (also in DTOMapper)](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/20)

### Youssef
- Added Submission
  - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/80)
- Added SubmissionData
  - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/81)
- Added functionality to determine all participants sorted by their scores for leaderboard
  - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/96)
- Added getLeaderboard, in GameService as well as corresponding endpoint and DTO
  - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/96)
- Assigned these issues to myself and worked on them partly, Nils worked also on these issues but did not see that I assigned them to myself, he finished earlier and pushed his part (voting and winingSubmission endpoint) 
  - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/39)
  - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/36)
  - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/67)
- Added GameGetDTO
  - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/90)
 
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
- [x] [UI for GameSummary Page](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/41)
- [x] [UI List of Winning Submissions for GameSummary](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/27)
- [x] [Website Background Deployment Fix](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/52)
- [x] [Added Ban Buttons to Submission Cards](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/60)
- [x] [Added Pick Buttons to Submission Cards](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/58)
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
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/96)
- Understanding testing environment and its functionalities and Setting it up
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/116)
- Adding tests for participant- and lobbyDTOMapper
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/117)
- Add test for LobbyRepository
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/118)
- Add test for GameRepository
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/119)
- Add test for GameToGameGetDTO
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/130)
- Add test for ParticipantToParticipantGetDTO
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/131)
- Add test for GameController
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/114)


---

## Week 4: [16.04. - 23.04]

### Georg
- Connect lobby to backend
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/131 
- Connect voting page to backend
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/132
- Connect round summary page to backend
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/134
- Connect game summary page to backend
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/133
- Implement functionality to detect players leaving the lobby/game by closing tab (backend and frontend)
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/135
    - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/168
- Restrict the summary time to 20 seconds
    - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/167
- End a phase within a round prematurely if everybody is done
    - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/157
    - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/156
- Fixed point awarding logic
    - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/148
- Store points gained for each player per round to display the point gain in the leaderboard
    - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/169
    - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/153
- Handle the case where less than 3 players are remaining (end game)
    - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/129

### Nils & Silvan
We worked together on these Issues on one device most of the time.
- [x] [Create Game Page Logic](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/94)
- [x] [Adding Listeners to Simple Map etc, using Google Maps JavaScript API to update current location data](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/95)
- [x] [Handle Google Maps javaScript API Simple Map calls](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/96)
- [x] [Handle Google Maps javaScript API Street View calls](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/97)
- [x] [API calls for submissions](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/98)
- [x] [Design of Google Maps Simple Maps](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/99)
- [x] [Add feature to accept noSubmission](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/100)

### Silvan
- [x] [Bugfix: Username can't enter lobby with username as empty string](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/118)

### Nils
- [x] [Fixed point awarding logic](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/148)

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
- Connect and adjust functionalities of JoinUser page (page where user should enter username and password of lobby)
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/71
- Connect JoinLobby page to backend, added and removed functionalities
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/72
- Connect CreateLobby page to backend, added and removed functionalities
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/73
- LobbyTable UI was totally reformatted
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/74
- Adjust JoinButton, CreateButton, Backbutton UI for extended use
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/75
- Create new model "Lobby"
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/77
- Added a new button specifically for creating a lobby
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/76
- Adjust guards and routers for entering a lobby in different cases
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/78
---

## Week 5: [23.04. - 30.04]

### Georg
- new waiting page that now is within same component
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/103
- cannot use the map/vote if you have submitted on reload
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/127  
- tests (mainly lobby service)
- make "empty" submissions submitted by "Can't find it" button work correctly
    - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/158
- include username on winning submission page (instead of anonymous name)
    - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/152  

### Silvan
- [x] [Tests for GameController](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/114)
- [x] [Added some tests for LobbyController](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/178)
#### Minor Bug fixes and additions:
- [x] [Changed Routing for GameSubmission](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/115)
- [x] [Favicon](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/146)
- [x] [Anonymous Animal Names correct ordering for all players](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/137)

### Ece
- Fix quest input field issue
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/111)
- Modify lobby settings for non-admin
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/114)
- Bug fixes:
    - Back navigation functionality: [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/109)
    - Fix background: [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/106)
    - Make UI responsive. Didn't work- transferred issue to Sprint 2. [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/124)

### Nils
- [x] [Added tests for DTOs and DTOMapper](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/179)
- [x] [Added tests for all repositories](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/180)
- [x] [Added some tests for LobbyController](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/178)
- [x] [Added some tests for entities](https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/181)

### Youssef
- Password field is disabled and greyed out 
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/108
- A dynamic resize of game summary instead of a static one based on the size of the window
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/123
- Adding a rank to the leaderboard, modifying DTO and Mapper
    - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/160
- Throwing errors if username is empty
    - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/155
- Checking http status of the whole backend 
    - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/154
- adding icons (streak, leadeboard) 
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/102
- redirect if there's a timeout
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/116
- adding fun facts (we decided afterwards to use an API in backend to generate them instead of hard coding)
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/152
- Voting icons for your own submission should be greyed out
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/120


## Week 6: [31.04. - 07.05]

### Silvan
- [x] UI Improvements: Icons for Buttons, Button Sizes, Button Locations, Lobby List Scrollable instead of Container, minor routing fix [Issue]([https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/114](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/155)
- [x] Progress Bar during Game [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/157)
- [x] UI Improvements Landing: Fonts, white space, logo resizing [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/168)

### Ece
- UI Improvements: Align things in the center in voting and game summary, worked on lobby layout and responsive container (ongoing)
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/156)
- Limit input field max char, add alert notifications in JoinUser and CreateLobby
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/175)
- Add how to instructions: general game rules in landing page + page-specific information on every page
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/154)

### Nils
- [x] Added new ui StreetViewModal, which includes a streetview panorama for each submission (restricted by options). Included it in current handleImageClick. Still not bug free. [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/176)
- [x] Added inputfield which autosaves for location using debouncing. Also not bugfree, did not manage to fix yet. [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/113)

### Georg
- fix issue where two lobbies could not have the same location
    - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/150
    - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/197
- randomize the submission order returned by the backend so that fastest submission is not necessarily in top left on the voting page
    - https://github.com/sopra-fs24-group-12/PeekSeek-server/issues/159
- rework notifications to be fancier using a new library
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/164
- hide a player's own submission on the voting page (s.t. only the other players' submissions are visible and clickable)
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/120

### Youssef
- [x] [Minor Bugfix: Username can't enter lobby with username as empty string](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/117)
- [x] [Tests for LobbyService and GameService](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/113)
- [x] [Tests for entities](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/181)

## Week 7: [08.05. - 14.05]

### Silvan
- [x] Timer UI and Animations for Game, Voting and Votingresults [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/122)
- [x] Router Guards, handle Routes to non-Existent pages with created modal [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/182)
- [x] Format Error Messages coming from backend with created Modal in several pages [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/163)

### Georg
- Add icons (current pointLeader, streak etc.)
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/102
- Pre-fetch elements before showing submissions and show skeleton
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/158
- Rework notifications during the game to use react-toastify
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/166
- Change activity ping timing and cancel on navigation to new page to avoid being kicked in rare cases
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/178
- Add a delay when transferring between different pages (loading symbol/animation)
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/179
- Redirect user to correct game page when trying to enter a page corresponding to a state the game is not in currently
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/184
- One save button instead of one for each part of settings
    - https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/113  

### Ece
- Made containers responsive
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/153)
- Reviewed all views' components to be responsive as well
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/187)
- Fixed lobby map to be responsive
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/112)
- Added animation on landing page
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/161)
- Added slider component to time per round
    - [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/162)
 
### Nils
- [x] Voting Page now has clickable images that then open up to Google Maps Streetview Panoramas, where you can even move around [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/176)
- [x] Clicking images no longer redirects to a blank page [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/125)
- [x] Add real GoogleMaps map to summary instead of static image (still minor bug open regarding data fetching) [Issue](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/126)

### Youssef
- [x] [Tests for LobbyService and GameService, mainly GameService](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/113)
- [x] [Tests for LobbyController](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/178)
- [x] [Tests for SummaryController (WebsocketController too, but it has been removed)](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/200)

## Week 8: [15.05. - 21.05]

### Nils
- [x] [Fixed open bugs on interactive on summary page](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/126)
- [x] [Added two new buttons that lead to a new tab with all markers (winning submissions). One button shows them in order of rounds, the other in shortest distance (from first to last submission) using Directions API](https://github.com/sopra-fs24-group-12/PeekSeek-client/issues/195)
