import processing.sound.*;

void setup() {
  //fullScreen(P2D);
  size(400, 800, P2D);
  //orientation(PORTRAIT);  

  init();
}

void draw() {
  switch(state) {
  case START_STATE:
    menuStart();
    break;
  case PLAY_STATE:
    gameActive();
    break;
  case GAMEOVER_STATE:
    gameOver();
    break;
  case CONFIG_STATE:
    config();
    break;
  }
}
