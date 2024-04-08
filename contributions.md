
# Contribution History

Record of contributions made to the project and the corresponding GitHub commit links.

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

### Nils
- Added voting, getLeaderboard endpoint
  - [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/961f6f84f55eeeac138adff3ebf91f31172ad4b7)
- Added APIService for GeoCoding-api
  - [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/9da6b21bdfb0db5b843663188893d352e8de432a)
- Added functionality to determine winningSubmission, calulatePoints, awardPoints
  - [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/9da6b21bdfb0db5b843663188893d352e8de432a)
- Changed lobbyService to check DB whether an api request to geoCoding api has already been made
  - [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/9da6b21bdfb0db5b843663188893d352e8de432a)
- Addded getSubmissions, getWinningSubmissions endpoint
  - [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/34ff3eab93cc2bbb22b7757da55cc482ff898a0e)
- Added SubmissionGetDTO and VotingPostDTO (also in DTOMapper)
  - [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/34ff3eab93cc2bbb22b7757da55cc482ff898a0e)
- Added getLeaderboard, postVoting functionality in GameService
  - [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/34ff3eab93cc2bbb22b7757da55cc482ff898a0e)

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
- Assigned these issues to myself and worked on them partly, Nils worked also on these issues but did not see that I assigned them to myself, he finished earlier and pushed his part
  - [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/34ff3eab93cc2bbb22b7757da55cc482ff898a0e)
- Added getLeaderboard, in GameService as well as corresponding endpoint and DTO
  - [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/d826d790bb6868abae5a28ce73f058abc643b3af)
- Added GameGetDTO
  - [Commit](https://github.com/sopra-fs24-group-12/PeekSeek-server/commit/38e23e74556503eb327e7b820411abf22a34564b)
---

## Week 3: [09.04. - 16.04]

### Georg

### Silvan

### Ece

### Nils

### Youssef

---

## Week 4: [16.04. - 23.04]

### Georg

### Silvan

### Ece

### Nils

### Youssef

---

## Week 5: [23.04. - 30.04]

### Georg

### Silvan

### Ece

### Nils

### Youssef

