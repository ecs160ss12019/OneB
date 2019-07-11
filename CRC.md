\# CRC Cards

 

\#\# Blocks.java:

\| Duties \| Interacts with \|

\|---\|---\|

\| Keeps track of how close one is to beating the level \| Ball.java

\| The object being hit in order to win the game \| Levels.java

\| Should act as a solid surface the ball can bounce off of. \| -

\| Should contain an update method to constantly check for ball collisions \| -

 

\#\# Ball.java

\| Duties \| Interacts with \|

\| --- \| --- \|

\| Accounts for the ball in the game that collides with other objects \|
Blocks.java

\| Should have functions that reverse it's direction and velocity \| Paddle.java

\| Should have a draw method to draw itsself \| PowerUps.java

\| -	\| BOGame.java

 

\#\# BOGame.java

\| Duties \| Interacts with \|

\| --- \| --- \|

\| Should be the thing that renders and controls the level in a high sense \|
Everything.

\| Should check for collisions and call the methods of the ball, paddles, and
blocks appropreately \| -

\| Should be in control of creating the level \| -

 

\#\# Paddle.java

\| Duties \| Interacts with\|

\|---\| ---\|

\| The paddle is whats used to hit the ball \| Ball.java

\| the user should be able to touch to mve the paddle \| User.javva

\| The paddle should only have move methods and a collider to tell the game it's
an object \| BOGame.java

 

\#\# Menu.java

\| Duties \| Interacts with \|

\| --- \| --- \|

\| The Menu should start the game and keep track of the leaderboard as well as
taking user input \| Database

\| More to add to this class later... \| -

 

 

 
