void menuStart() {
  backdrop.render();
  
  if (!playingmusicTheme) {
    theme.play(); 
    playingmusicTheme=true;
  }
  
  menuLayout();
}

void menuButtons() {
  if ((mouseY>h/2.5-b*0.25)&&(mouseY<h/2.5+b*0.25)) {

    cometSpawn();
    theme.pause();
    state=PLAY_STATE;
  }
  if ((mouseY>h*0.9)) {
    state=CONFIG_STATE;
  }
}

void config() {

  float diffButtonStart=h*0.25;
  float diffButtonEnd=h*0.35;

  backdrop.render();
  configLayout();

  configBall=b/8+b*0.07*difficulty;
  if ((mousePressed)&&(mouseY<diffButtonEnd)&&(mouseY>diffButtonStart)) {
    difficulty=(mouseX-(b/6))/(b/15)+1;
  }
  if (difficulty<1) {
    difficulty=1;
  } else if (difficulty>10) {
    difficulty=10;
  }
  
}

void backToMenu() {
  if ((mouseY>height*0.85-cDia*0.75)&&(mouseY<height*0.85+cDia*0.75)&&(mouseX<width/2)) {
    if (cHigh.graphics) {
      cHigh.graphics=false;
      graphics=false;
    } else {
      cHigh.graphics=true;
      graphics=true;
    }
  }

  if ((mouseY>height*0.85-cDia*0.75)&&(mouseY<height*0.85+cDia*0.75)&&(mouseX>width/2)) {
    if (sound) {
      sound=false;
    } else {
      sound=true;
    }
  }

  if ((mouseY>h*0.4)&&(mouseY<h*0.6)) {
    state=START_STATE;
  }
}
