Gustavo Diaz Banuelos

Description:
        I want to create a very simple NES Legend of Zelda clone. The game is a top down
    2D game where the player controls "Link" and moves around the world. There are some
    enemies that move towards the enemy and the player has a sword or a bow to attack.
    If the enemies touch Link, then he loses health and if Link defeats enemies he gets money.

MVC:
        The models for this game would be Link, the enemies and the gameboard. Link and the enemies have common
    properties like health and movementspeed. Link can hold money and would have a score
    associated to how many enemies he has defeated. There will be set chests on the board the
    provide certain bonuses.The View would be show the health, score, and money that Link has currently.
    The Controller would communcate the infomation to the model and the view so that the player has both
    control and can view the info about the current session.

Clore Classes:
    Models:
        Arrow.java
        Enemy.java
        GameBoard.java
        Player.java

    Controller.java
    View.java
    Main.java

Extra:
    I have been implementing some of the movement already. Link can move off the board so need to fix that.
    Added some sprites and a chest to the gameboard.
