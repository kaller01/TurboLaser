class Score {
  int score;
  boolean[] level;
  boolean[] powerTaken;
  int upgrade1=int(random(20, 22));
  int upgrade2=int(random(150, 200));
  int upgrade3=int(random(270, 350));
  boolean[] upgradeTaken;

  Score() {
    level= new boolean[99];
    for (int i=0; i < 98; i++) {
      level[i]=false;
    }
    powerTaken = new boolean[20];
    for (int i=0; i < 20; i++) {
      powerTaken[i] = false;
    }
    upgradeTaken = new boolean [3];
    for (int i=0; i < 3; i++) {
      upgradeTaken[i] = false;
    }
  }

  void add() {
    score+=1;
  }

  void restore() {
    score=0;
  }

  void render() {
    textAlign(CENTER);
    fill(255);
    textFont(font[1]);
    text("Score", width*0.2, height*0.94);
    textFont(font[2]);
    text(score, width*0.2, height*0.985);
  }

  void level() {
    if (score==25) {
      if (!level[0]) {
        cometer.add(new Comet(cDia, graphics, random(minSpeed, maxSpeed)));
        level[0]=true;
      }
    }
    if (score==50) {
      if (!level[1]) {
        minSpeed=cDia/14;
        level[1]=true;
      }
    }
    if (score==100) {
      if (!level[2]) {
        cometer.add(new Comet(cDia, graphics, random(minSpeed, maxSpeed)));
        level[2]=true;
      }
    }
    if (score==150) {
      if (!level[3]) {
        minSpeed=cDia/13;
        level[3]=true;
      }
    }
    if (score==200) {
      if (!level[4]) {
        minSpeed=cDia/12;
        level[4]=true;
      }
    }
    if (score==250) {
      if (!level[5]) {
        minSpeed=cDia/11;
        level[5]=true;
      }
    }
    if (score==300) {
      if (!level[6]) {
        maxSpeed=cDia/9;
        level[6]=true;
      }
    }
    for (int i=7; i < 91; i++) {
      if (score==100*i-350) {
        if (!level[i]) {
          cometer.add(new Comet(cDia, graphics, random(minSpeed, maxSpeed)));
          level[i]=true;
        }
      }
    }
    for (int i=0; i < 20; i++) {
      if (score==10+100*i) {
        if (!powerTaken[i]) {
          power.add(new LightSheild());
          powerTaken[i]=true;
        }
      }
    }
    if (score==upgrade1 && !upgradeTaken[0]) {
      upgrade.add(new RapidLaser());
      upgradeTaken[0] = true;
    }
    if (score==upgrade2 && !upgradeTaken[1]) {
      upgrade.add(new RapidLaser());
      upgradeTaken[1] = true;
    }
    if (score==upgrade3 && !upgradeTaken[2]) {
      upgrade.add(new RapidLaser());
      upgradeTaken[2] = true;
    }
  }
}
