//Gamecore, gameActive
void gameActive() {

  backdrop.render();
  pushMatrix();
  translate(width/2, height*0.9);
  //Draws sheild bubble
  if (lightACTIVE) {
    image(light, 0, -height*0.05);
  }

  //Aimsturret CALC
  if (mouseY>height*0.75) {
    aimTurret = new PVector(mouseX, height*0.75);
  } else {
    aimTurret = new PVector(mouseX, mouseY);
  }

  laserStart = new PVector(width/2, height*0.9);
  turretCenter = new PVector(width/2, height*0.9);


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
void laserAdd() {
  if (mouseY>height*0.75) {
    aim = new PVector(mouseX, height*0.75);
  } else {
    aim = new PVector(mouseX, mouseY);
  }
  if (sound && !rapidLaserACTIVE) laser.play();
  lasers.add(new Laser(aim, laserStart));
}

void keyPressed() {
}
