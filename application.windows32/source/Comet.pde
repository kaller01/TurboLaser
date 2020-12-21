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
      crateX[i]=random((Dia/-2)+Dia*0.2, (Dia/2)-Dia*0.2);
      crateY[i]=random((Dia/-2)+Dia*0.2, (Dia/2)-Dia*0.2);
    }
    graphics=setting;
  }


  void fall() {
    y += vy;
  }

  void render() {
    noStroke();
    if (graphics) {
      fill(255, 0, 0);
      ellipse(x+random(-1, 1), y-0.76*Dia+random(Dia*-0.04, Dia*0.08), Dia*0.76, Dia*0.76);
      fill(255, 140, 0);
      ellipse(x+random(-1, 1), y-0.6*Dia+random(Dia*-0.04, Dia*0.08), Dia*0.82, Dia*0.82);
      fill(255, 215, 0);
      ellipse(x+random(-1, 1), y-0.4*Dia+random(Dia*-0.04, Dia*0.08), Dia*0.85, Dia*0.85);
      fill(255, 255, 0);
      ellipse(x+random(-1, 1), y-0.2*Dia+random(Dia*-0.04, Dia*0.08), Dia*0.94, Dia*0.94);
    } else {
      fill(255, 0, 0);
      ellipse(x, y-0.76*Dia, Dia*0.76, Dia*0.76);
      fill(255, 140, 0);
      ellipse(x, y-0.6*Dia, Dia*0.82, Dia*0.82);
      fill(255, 215, 0);
      ellipse(x, y-0.4*Dia, Dia*0.85, Dia*0.85);
      fill(255, 255, 0);
      ellipse(x, y-0.2*Dia, Dia*0.94, Dia*0.94);
    }
    fill(150);
    ellipse(x, y, Dia, Dia);
    for (int i=0; i < crateX.length; i++) {
      fill(120-3*i);
      ellipse(x+crateX[i], y+crateY[i], Dia*0.1, Dia*0.1);
    }
  }

  //Checks if comet is out of frame
  boolean cometOutofBorder() {
    if (y>20) {
      return true;
    } else {
      return false;
    }
  }

  //Explode Animation
  void cometExplode() {
    fill(255, 0, 0);
    ellipse(x, y, 1.3*Dia, 1.3*Dia);
  }


  //Base Explode Animation
  void cometBoom() {
    noStroke();
    fill(255, 0, 0);
    ellipse(x, 0, CometDiameter*2, CometDiameter*2);
    fill(255, 140, 0);
    ellipse(x, 0, CometDiameter*1.5, CometDiameter*1.5);
    fill(255, 215, 0);
    ellipse(x, 0, CometDiameter, CometDiameter);
  }
}
