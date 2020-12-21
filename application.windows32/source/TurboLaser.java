import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.sound.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class TurboLaser extends PApplet {



public void setup() {
  //fullScreen(P2D);
  
  orientation(PORTRAIT);  

  init();
}

public void draw() {
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
class Comet {
  float Dia;
  float CometDiameter;
  float[] crateX = new float[8];
  float[] crateY = new float[8];
  boolean graphics;

  float x=random((-width/2)+(cDia/2), (width/2)-(cDia/2));
  float vy;
  float y=-height;

  Comet(float DiaTemp, boolean setting, float vy) {
    Dia=DiaTemp;
    CometDiameter=DiaTemp;
    this.vy=vy;
    for (int i=0; i < crateX.length; i++) {
      crateX[i]=random((Dia/-2)+Dia*0.2f, (Dia/2)-Dia*0.2f);
      crateY[i]=random((Dia/-2)+Dia*0.2f, (Dia/2)-Dia*0.2f);
    }
    graphics=setting;
  }


  public void fall() {
    y += vy;
  }

  public void render() {
    noStroke();
    if (graphics) {
      fill(255, 0, 0);
      ellipse(x+random(-1, 1), y-0.76f*Dia+random(Dia*-0.04f, Dia*0.08f), Dia*0.76f, Dia*0.76f);
      fill(255, 140, 0);
      ellipse(x+random(-1, 1), y-0.6f*Dia+random(Dia*-0.04f, Dia*0.08f), Dia*0.82f, Dia*0.82f);
      fill(255, 215, 0);
      ellipse(x+random(-1, 1), y-0.4f*Dia+random(Dia*-0.04f, Dia*0.08f), Dia*0.85f, Dia*0.85f);
      fill(255, 255, 0);
      ellipse(x+random(-1, 1), y-0.2f*Dia+random(Dia*-0.04f, Dia*0.08f), Dia*0.94f, Dia*0.94f);
    } else {
      fill(255, 0, 0);
      ellipse(x, y-0.76f*Dia, Dia*0.76f, Dia*0.76f);
      fill(255, 140, 0);
      ellipse(x, y-0.6f*Dia, Dia*0.82f, Dia*0.82f);
      fill(255, 215, 0);
      ellipse(x, y-0.4f*Dia, Dia*0.85f, Dia*0.85f);
      fill(255, 255, 0);
      ellipse(x, y-0.2f*Dia, Dia*0.94f, Dia*0.94f);
    }
    fill(150);
    ellipse(x, y, Dia, Dia);
    for (int i=0; i < crateX.length; i++) {
      fill(120-3*i);
      ellipse(x+crateX[i], y+crateY[i], Dia*0.1f, Dia*0.1f);
    }
  }

  //Checks if comet is out of frame
  public boolean cometOutofBorder() {
    if (y>20) {
      return true;
    } else {
      return false;
    }
  }

  //Explode Animation
  public void cometExplode() {
    fill(255, 0, 0);
    ellipse(x, y, 1.3f*Dia, 1.3f*Dia);
  }


  //Base Explode Animation
  public void cometBoom() {
    noStroke();
    fill(255, 0, 0);
    ellipse(x, 0, CometDiameter*2, CometDiameter*2);
    fill(255, 140, 0);
    ellipse(x, 0, CometDiameter*1.5f, CometDiameter*1.5f);
    fill(255, 215, 0);
    ellipse(x, 0, CometDiameter, CometDiameter);
  }
}
//Gamecore, gameActive
public void gameActive() {

  backdrop.render();
  pushMatrix();
  translate(width/2, height*0.9f);
  //Draws sheild bubble
  if (lightACTIVE) {
    image(light, 0, -height*0.05f);
  }

  //Aimsturret CALC
  if (mouseY>height*0.75f) {
    aimTurret = new PVector(mouseX, height*0.75f);
  } else {
    aimTurret = new PVector(mouseX, mouseY);
  }

  laserStart = new PVector(width/2, height*0.9f);
  turretCenter = new PVector(width/2, height*0.9f);


  //renderss and shots laser
  for (int i=0; i < lasers.size(); i++) {
    Laser l = lasers.get(i);
    l.render();
    l.shoot();

    //Removes the laser when out of border
    if ((l.laser.y<-height)||(l.laser.x<-width)||(l.laser.x>width)) {
      lasers.remove(i);
    }
  }
  //renders comets
  for (int i=0; i < cometer.size(); i++) {
    Comet c=cometer.get(i);
    c.render();
    c.fall();

    //If Comet is in dist of laser, explode and remove from ArrayList
    //If Laser is in dist of comet, remove from ArrayList
    for (int n=0; n < lasers.size(); n++) {
      Laser l=lasers.get(n);
      if (l.hit(c.x, c.y)) {
        c.cometExplode();
        cometer.remove(i);
        lasers.remove(n);
        score.add();
        cometer.add(new Comet(cDia, graphics, random(minSpeed, maxSpeed)));
      }
    }

    //If comet is >height respawn
    if (c.cometOutofBorder()) {
      cometer.remove(i);
      cometer.add(new Comet(cDia, graphics, random(minSpeed, maxSpeed)));
      if (lightACTIVE) {
        lightACTIVE=false;
        sheildDOWN.play();
      } else {
        hp.decrease();
        //If hp is less than 1 = gameover
        if (hp.death()) {
          backdrop.explodeCoords(c.x);
          state=GAMEOVER_STATE;
        }
      }
    }

    //If comet is >height explode animation.
    if (c.y>0 && !lightACTIVE) {
      if (hp.hp>1) {
        c.cometBoom();
      }
    }
  }
  //LightSheild
  for (int i=0; i < power.size(); i++) {
    LightSheild p=power.get(i);
    p.fall();
    p.draw();
    //If lightsheild reach turret base, apply sheild
    if (p.y>0) {
      lightACTIVE=true;
      sheildUP.play();
      power.remove(i);
    }
    //Destroy LightSheildBULB if hit with laser
    for (int n=0; n < lasers.size(); n++) {
      Laser l=lasers.get(n);
      if (l.hit(p.x, p.y)) {
        power.remove(i);
        lasers.remove(n);
      }
    }
  }
  
  //RapidLaser
  for (int i=0; i < upgrade.size(); i++) {
    RapidLaser p=upgrade.get(i);
    if (!p.boom) p.fall();
    if (p.boom) p.boom();
    p.draw();
    //If laser bubble reach turret base, apply upgrade
    if (p.y>0) {
      rapidLaserACTIVE=true;
      upgrade.remove(i);
    }
    //Destroy laser bubble if hit with laser
    for (int n=0; n < lasers.size(); n++) {
      Laser l=lasers.get(n);
      if (l.hit(p.x, p.y)) {
        p.boom=true;
        lasers.remove(n);
      }
    }
    if (p.dia>height*2) {
      upgrade.remove(i);
    }
    if (p.death()) { 
      hp.decrease();
      p.death=true;
    }
    if (hp.death() && p.boom) {
      backdrop.explodeCoords(p.x);
      state=GAMEOVER_STATE;
    }
  }
  if (rapidLaserACTIVE) rapidLaserACTIVE();



  //draw turret
  backdrop.aim(aimTurret, turretCenter);
  backdrop.turret();
  popMatrix();

  //STATS
  hp.render();
  score.render();
  score.level();
}

//Add new Laser to ArrayList if game is active
public void laserAdd() {
  if (mouseY>height*0.75f) {
    aim = new PVector(mouseX, height*0.75f);
  } else {
    aim = new PVector(mouseX, mouseY);
  }
  if (sound && !rapidLaserACTIVE) laser.play();
  lasers.add(new Laser(aim, laserStart));
}

public void keyPressed() {
}
public void gameOver() {
  backdrop.render();
  pushMatrix();
  translate(width/2, height*0.9f);
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

public void gameoverStage1() {
  fill(0);
  textAlign(CENTER, CENTER);
  textFont(font[0]);
  text("Game over", width/2, height*0.2f);
  textFont(font[3]);
  text("Score: "+score.score, width/2, height*0.27f);
}

public void gameoverStage2() {
  fill(255);
  noStroke();
  fill(255);
  tri(width/2, height*0.45f);
  if (mousePressed) {
    if ((mouseY> height*0.35f)&&(mouseY<height*0.55f)) {
      imperial.pause();
      init();
      cometSpawn();
      state=2;
    }
  }
}

public void gamoverStage3() {
  textFont(font[2]);
  fill(0);
  textAlign(CENTER, CENTER);
  text("Exit", width/2, height*0.85f);
  if (mousePressed) {
    if (mouseY>height*0.80f) {
      imperial.pause();
      init();
      state=1;
    }
  }
}
class Laser {

  PVector laser= new PVector(0, 0);
  PVector velocity;

  Laser(PVector aim, PVector turretCenter) {
    velocity=aim;
    velocity.sub(turretCenter);
    velocity.setMag(height/27);
  }

  public void render() {
    //To check hitboxes
    //fill(255, 0, 0);
    //ellipse(laser.x-velocity.x*0.75, laser.y-velocity.y*0.75, cDia/2+height/54, cDia/2+height/54);
    strokeWeight(5*ts);
    stroke(100, 255, 100);
    line(laser.x-velocity.x*1.5f+velocity.y/3, laser.y-velocity.y*1.5f-velocity.x/3, laser.x+velocity.y/3, laser.y-velocity.x/3);
    line(laser.x-velocity.x*1.5f-velocity.y/3, laser.y-velocity.y*1.5f+velocity.x/3, laser.x-velocity.y/3, laser.y+velocity.x/3);
  }

  public void shoot() {
    laser.add(velocity);
  }

  //Checks if Laser hits target
  public boolean hit(float targetX, float targetY) {
    float d= dist(laser.x-velocity.x*0.75f, laser.y-velocity.y*0.75f, targetX, targetY);
    if ( d < cDia/2+height/54) {
      return true;
    } else {
      return false;
    }
  }
}
public void menuStart() {
  backdrop.render();
  
  if (!playingmusicTheme) {
    theme.play(); 
    playingmusicTheme=true;
  }
  
  menuLayout();
}

public void menuButtons() {
  if ((mouseY>h/2.5f-b*0.25f)&&(mouseY<h/2.5f+b*0.25f)) {

    cometSpawn();
    theme.pause();
    state=PLAY_STATE;
  }
  if ((mouseY>h*0.9f)) {
    state=CONFIG_STATE;
  }
}

public void config() {

  float diffButtonStart=h*0.25f;
  float diffButtonEnd=h*0.35f;

  backdrop.render();
  configLayout();

  configBall=b/8+b*0.07f*difficulty;
  if ((mousePressed)&&(mouseY<diffButtonEnd)&&(mouseY>diffButtonStart)) {
    difficulty=(mouseX-(b/6))/(b/15)+1;
  }
  if (difficulty<1) {
    difficulty=1;
  } else if (difficulty>10) {
    difficulty=10;
  }
  
}

public void backToMenu() {
  if ((mouseY>height*0.85f-cDia*0.75f)&&(mouseY<height*0.85f+cDia*0.75f)&&(mouseX<width/2)) {
    if (cHigh.graphics) {
      cHigh.graphics=false;
      graphics=false;
    } else {
      cHigh.graphics=true;
      graphics=true;
    }
  }

  if ((mouseY>height*0.85f-cDia*0.75f)&&(mouseY<height*0.85f+cDia*0.75f)&&(mouseX>width/2)) {
    if (sound) {
      sound=false;
    } else {
      sound=true;
    }
  }

  if ((mouseY>h*0.4f)&&(mouseY<h*0.6f)) {
    state=START_STATE;
  }
}
class LightSheild {
  float x;
  float y;
  float dia=cDia;
  boolean spawn;
  float vy=random(minSpeed, maxSpeed);

  LightSheild() {
    x=random(-width/2, width/2);
    y=-height;
  }

  public void draw() {

    fill(240, 248, 255);
    ellipse(x+random(-1, 1), y-0.6f*dia+random(dia*-0.04f, dia*0.08f), dia*0.85f, dia*0.85f);
    fill(135, 206, 250);
    ellipse(x+random(-1, 1), y-0.3f*dia+random(dia*-0.04f, dia*0.08f), dia*0.94f, dia*0.94f);
    fill(0, 191, 255);
    ellipse(x, y, dia, dia);
  }
  public void fall() {
    y+=vy;
  }
}

class RapidLaser {
  float x;
  float y;
  float dia=cDia;
  boolean spawn;
  float vy=random(minSpeed, maxSpeed);
  boolean boom=false;
  boolean death=false;
  float time=0;

  RapidLaser() {
    x=random(-width/2, width/2);
    y=-height;
  }

  public void draw() {
    if (boom) {
      noFill();
      stroke(100, 255, 100);
      strokeWeight(dia/5);
    } else {
      noStroke();
      fill(100, 255, 100);
    }
    ellipse(x, y, dia, dia);
  }
  public void fall() {
    y+=vy;
  }

  public void boom() {
    dia+=ts*30;
  }

  public boolean death() {
    if (dia/2+y>0 && boom && !death) {
      return true;
    } else return false;
  }
}

public void rapidLaserACTIVE() {
  if (rapidLaserTime<0) rapidLaserACTIVE=false;
  if (mousePressed) {
    laserAdd();
    rapidLaserTime-=1;
  }
}
class Backdrop {
  int stars=100;
  float[] starx= new float[stars];
  float[] stary= new float[stars];
  float x;
  float dia=0;
  float filltime=0;
  float time=0;
  PVector velocity;

  public void render() {
    rectMode(CORNER);
    noStroke();
    for (int i=0; i < 81; i++) {
      float h=height/8;
      fill(15+0.2f*i, 20+0.2f*i, 40+0.75f*i);
      rect(0, 0+0.1f*h*i, width, 0.1f*h);
    }
    for (int i=0; i < stars; i++) {
      fill(255); 
      ellipse(starx[i], stary[i], 5, 5);
    }
  }


  public void generatestars() {
    for (int i=0; i < stars; i++) {
      starx[i]=random(width);
      stary[i]=random(height);
    }
  }

  public void shake() {
    for (int i=0; i < stars; i++) {
      starx[i]=0;
      stary[i]=0;
    }
  }

  public void explodeCoords(float xTemp) {
    x=xTemp+width/2;
  }
  
  public void explode() {
    noStroke();
    fill(255, 0, 0);
    ellipse(x, height*0.9f, dia*2, dia*2);
    fill(255, 140, 0);
    ellipse(x, height*0.9f, dia*1.5f, dia*1.5f);
    fill(255, 215-filltime, 0);
    ellipse(x, height*0.9f, dia, dia);
    
    if (time>0 && time<20) {
      dia=dia+10*ts;
    } else if (time>20 && time<30) {
      dia=dia-20*ts;
    } else if (time>38) {
      if (dia<width) dia=dia+10*ts*2;
      if (dia>width) dia=dia+5*ts*2;
      if (dia>height*1.5f) {
        filltime=filltime+1;
      }
    }
    time+=1;
  }
  
  public void aim(PVector aim1, PVector turretCenter1){
    
    velocity=aim1;
    velocity.sub(turretCenter1);
    velocity.setMag(height/16);
  }
  public void turret() {
    noStroke();
    fill(50);
    rect(-width/2, 0, width, height*0.1f);
    fill(80);
    rectMode(CENTER);
    rect(0, 0+height*0.015f, width*0.2f, height*0.05f);


    stroke(200);
    strokeWeight(5*ts);

    line(velocity.x-velocity.x+velocity.y/5, velocity.y-velocity.y-velocity.x/5, velocity.x+velocity.y/5, velocity.y-velocity.x/5);
    line(velocity.x-velocity.x-velocity.y/5, velocity.y-velocity.y+velocity.x/5, velocity.x-velocity.y/5, velocity.y+velocity.x/5);


    noStroke();
    fill(120);
    ellipse(0, 0, height/20, height/20);
  }
}
public void cometSpawn() {
  for (int i=0; i < difficulty; i++) {
    cometer.add(new Comet(cDia, graphics, random(minSpeed, maxSpeed)));
  }
}

public void menuLayout() {
  rectMode(CENTER);

  fill(255);
  triangle(b/2-b*0.25f, h/2.5f-b*0.25f, b/2-b*0.25f, h/2.5f+b*0.25f, b/2+b*0.25f, h/2.5f);
  textSize(height/35);
  textAlign(CENTER);


  menuComet.y=h/2-h*0.3f;
  menuComet.x=b/2+b*0.2f;
  menuComet.render();

  fill(255);
  rect(b/4.5f, h-h/15, b/4, b/80);
  ellipse(b/4.5f-b/15, h-h/15, b/15, b/15);

  noStroke();
  translate(b/2+b*0.25f, h/2+h*0.35f);
  rotate(radians(45*3));

  rectMode(CENTER);
  fill(50);
  rect(0, -b/2.1f, width*2, b);
  fill(80);
  rect(0, -b/30, b/4, b/6);

  fill(200);
  rect(-b/35, b/10, b/60, b/7.5f);
  rect(b/35, b/10, b/60, b/7.5f);
  fill(120);
  noStroke();
  ellipse(0, 0, b/7.5f, b/7.5f);  

  fill(100, 255, 100);
  rect(-b/35, b/3, b/70, b/11);
  rect(b/35, b/3, b/70, b/11);
  rect(-b/35, b/1.7f, b/70, b/11);
  rect(b/35, b/1.7f, b/70, b/11);
}

public void tri(float x, float y) {
  triangle(x-width*0.2f, y-width*0.2f, x-width*0.2f, y+width*0.2f, x+width*0.2f, y);
}

public void configLayout() {
  textAlign(CENTER, CENTER);
  fill(255);
  textFont(font[0]);
  text("Difficulty "+difficulty, b/2, h*0.2f);


  rectMode(CENTER);
  rect(b/2-b*0.1f, h*0.5f, b/15, b/3);
  rect(b/2+b*0.1f, h*0.5f, b/15, b/3);

  cHigh.x=width/4;
  cHigh.y=height*0.85f;
  cHigh.render();
  textFont(font[1]);
  fill(255);
  text("Graphics: "+cHigh.graphics, width/4, height*0.9f);

  text("Sounds: "+sound, width*0.75f, height*0.9f);
  pushMatrix();
  translate(width*0.75f, height*0.85f);
  rotate(radians(180));
  fill(200);
  rect(-b/35, b/10, b/60, b/7.5f);
  rect(b/35, b/10, b/60, b/7.5f);
  fill(120);
  noStroke();
  ellipse(0, 0, b/7.5f, b/7.5f);
  popMatrix();

  fill(255);
  rect(b/2, h*0.3f, b/1.5f, b/80);
  ellipse(configBall, h*0.3f, b/15, b/15);
}
class HealthPoints {
  float hp=4;

  public void decrease() {
    if (hp>1) {
      hp=hp-1;
      boom.play();
    } else {
      hp=hp-1;
      implode.play();
    }
  }

  public void restore() {
    hp=4;
  }

  public void render() {
    for (int i=0; i < hp; i++) {
      fill(255, 0, 0); 
      ellipse(width*0.65f+width*0.1f*i, height*0.97f, width/16, width/16);
    }
    textAlign(CENTER);
    fill(255);
    textFont(font[1]);
    text("Health", width*0.8f, height*0.94f);
  }

  public boolean death() {
    if (hp<=0) {
      return true;
    } else {
      return false;
    }
  }
}
public void mousePressed() {
  switch(state){
     case PLAY_STATE:
     if(!rapidLaserACTIVE) laserAdd();
     break;
     case START_STATE:
     menuButtons();
     break;
     case CONFIG_STATE:
     backToMenu();
     break;
  }
  
}
class Score {
  int score;
  boolean[] level;
  boolean[] powerTaken;
  int upgrade1=PApplet.parseInt(random(20, 22));
  int upgrade2=PApplet.parseInt(random(150, 200));
  int upgrade3=PApplet.parseInt(random(270, 350));
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

  public void add() {
    score+=1;
  }

  public void restore() {
    score=0;
  }

  public void render() {
    textAlign(CENTER);
    fill(255);
    textFont(font[1]);
    text("Score", width*0.2f, height*0.94f);
    textFont(font[2]);
    text(score, width*0.2f, height*0.985f);
  }

  public void level() {
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
//Screen state
final int START_STATE=1;
final int PLAY_STATE=2;
final int GAMEOVER_STATE=3;
final int CONFIG_STATE=4;
final int TEST_STATE=5;
int state=1;

//Gamecore
int difficulty=1;
float minSpeed;
float maxSpeed;
boolean graphics=true;
Backdrop backdrop;
ArrayList<Comet> cometer;
ArrayList<Laser> lasers= new ArrayList();
PVector aim;
PVector laserStart;
PVector aimTurret;
PVector turretCenter;
HealthPoints hp = new HealthPoints();
Score score = new Score();
ArrayList<LightSheild> power;
ArrayList<RapidLaser> upgrade;
boolean lightACTIVE;
boolean rapidLaserACTIVE;
PImage light;
float rapidLaserTime;

//Menu
boolean playingmusicTheme;
boolean playingmusicImperial;
float configBall;
int h;
int b;
SoundFile music;
SoundFile theme;
SoundFile imperial;
SoundFile laser;
SoundFile boom;
SoundFile implode;
SoundFile sheildUP;
SoundFile sheildDOWN;
Comet menuComet;
float cDia;
Comet cHigh;
PFont[] font= new PFont[6];
int ts;

//Gameover
float time;
boolean sound=true;

public void init() {
  cDia=height/13;
  backdrop = new Backdrop();
  minSpeed=cDia/15;
  maxSpeed=cDia/10;
  menuComet = new Comet((cDia*2), true, 1);
  ts=height/800;
  font[0]= createFont("Century Gothic", ts*60);
  font[1]= createFont("Century Gothic bold", ts*25);
  font[2]= createFont("Century Gothic bold", ts*40);
  font[3]= createFont("Century Gothic", ts*40);

  time=0;
  hp.restore();
  score = new Score();
  cometer= new ArrayList<Comet>();
  b=width;
  h=height;

  playingmusicTheme=false;
  playingmusicImperial=false;
  music = new SoundFile(this, "music.wav");
  theme = new SoundFile(this, "theme.wav");
  imperial = new SoundFile(this, "imperial.wav");
  laser = new SoundFile(this, "laser.wav");
  boom = new SoundFile(this, "alarm.wav");
  implode = new SoundFile(this, "implode.wav");
  sheildUP = new SoundFile(this, "sheildUP.wav");
  sheildDOWN = new SoundFile(this, "sheildDOWN.wav");

  backdrop.generatestars();

  cHigh = new Comet(cDia*1, graphics, 1);

  power = new ArrayList<LightSheild>();
  upgrade = new ArrayList<RapidLaser>();
  lightACTIVE=false;
  rapidLaserACTIVE=false;
  light=loadImage("light.png");
  light.resize(width, width/5);
  imageMode(CENTER);
  rapidLaserTime=300;
}
  public void settings() {  size(400, 800, P2D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "TurboLaser" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
