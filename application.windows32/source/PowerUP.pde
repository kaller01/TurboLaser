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

  void draw() {

    fill(240, 248, 255);
    ellipse(x+random(-1, 1), y-0.6*dia+random(dia*-0.04, dia*0.08), dia*0.85, dia*0.85);
    fill(135, 206, 250);
    ellipse(x+random(-1, 1), y-0.3*dia+random(dia*-0.04, dia*0.08), dia*0.94, dia*0.94);
    fill(0, 191, 255);
    ellipse(x, y, dia, dia);
  }
  void fall() {
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

  void draw() {
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
  void fall() {
    y+=vy;
  }

  void boom() {
    dia+=ts*30;
  }

  boolean death() {
    if (dia/2+y>0 && boom && !death) {
      return true;
    } else return false;
  }
}

void rapidLaserACTIVE() {
  if (rapidLaserTime<0) rapidLaserACTIVE=false;
  if (mousePressed) {
    laserAdd();
    rapidLaserTime-=1;
  }
}
