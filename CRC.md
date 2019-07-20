# CRC Cards

## BOBlocks.java:
| Duties | Interacts with |
|---|---|
| Keeps track of how close one is to beating the level | Ball.java
| The object being hit in order to win the game | Levels.java
| Should act as a solid surface the ball can bounce off of. | -
| Should contain an update method to constantly check for ball collisions | -

## BOBall.java
| Duties | Interacts with |
| --- | --- |
| Accounts for the ball in the game that collides with other objects | Blocks.java
| Should have functions that reverse it's direction and velocity | Paddle.java
| Should have a draw method to draw itsself | PowerUps.java 
| -	| BOGame.java 

## BOGame.java 
| Duties | Interacts with |
| --- | --- |
| Should be the thing that renders and controls the level in a high sense | Everything. 
| Should check for collisions and call the methods of the ball, paddles, and blocks appropriately | -
| Should be in control of creating the level | -

## BOGameController.java 
| Duties | Interacts with |
| --- | --- |
| Should be the thing controls state machine of the game | Everything. 
| Should initialize objects for the rest of the game like the paddle, ball and music | -
| Should be able to pause and resume the game| -


## BOObject.java 
| Duties | Interacts with| 
|---| ---|
| This class is an abstraction for all the other game objects that appear on the screen | -
| stores information about the position and collider for that object | -
| allows a sprite to be assigned to the game object | -


## BOPaddle.java 
| Duties | Interacts with| 
|---| ---|
| Hits the ball back and fourth on the screen| BOBall.java
| the user should be able to touch to mve the paddle | User.java
| The paddle should only have move methods and a collider to tell the game it's an object | BOGame.java

## BOMenu.java 
| Duties | Interacts with |
| --- | --- |
| The Menu should start the game and keep track of the leaderboard as well as taking user input | Database 
| More to add to this class later... | -

## BOMenuButton.java 
| Duties | Interacts with |
| --- | --- |
| allows a button in the menu to be created at a specific position | BOMenu.java 
| also allows the game to be paused if the button is a pause button | -

## BOTimer.java 
| Duties | Interacts with |
| --- | --- |
| allows a timer to be set for a certain amount of time in seconds | Everything
| return true on completion so a calling class can know when a timer is done| -

## BOLayout.java 
| Duties | Interacts with |
| --- | --- |
| allows a layout for the game's screen to be created| Everything
| gives the new layout a collider| -


