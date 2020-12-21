void gameOver() {
  backdrop.render();
  pushMatrix();
  translate(width/2, height*0.9);
  backdrop.turret();
  popMatrix();
  backdrop.explode();
  time=time+1;
  if (time>38) {
  }
  if (time>30) {

    gameoverStage1();
  }

  if (time>60) {
    if (!playingmusicImperial) {
      imperial.play(); 
      playingmusicImperial=true;
    }
  }

  if (time>140) {
    gameoverStage2();
  }

  if (time>180) {
    gamoverStage3();
  }
}

void gameoverStage1() {
  fill(0);
  textAlign(CENTER, CENTER);
  textFont(font[0]);
  text("Game over", width/2, height*0.2);
  textFont(font[3]);
  text("Score: "+score.score, width/2, height*0.27);
}

void gameoverStage2() {
  fill(255);
  noStroke();
  fill(255);
  tri(width/2, height*0.45);
  if (mousePressed) {
    if ((mouseY> height*0.35)&&(mouseY<height*0.55)) {
      imperial.pause();
      init();
      cometSpawn();
      state=2;
    }
  }
}

void gamoverStage3() {
  textFont(font[2]);
  fill(0);
  textAlign(CENTER, CENTER);
  text("Exit", width/2, height*0.85);
  if (mousePressed) {
    if (mouseY>height*0.80) {
      imperial.pause();
      init();
      state=1;
    }
  }
}
