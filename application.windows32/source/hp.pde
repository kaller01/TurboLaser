class HealthPoints {
  float hp=4;

  void decrease() {
    if (hp>1) {
      hp=hp-1;
      boom.play();
    } else {
      hp=hp-1;
      implode.play();
    }
  }

  void restore() {
    hp=4;
  }

  void render() {
    for (int i=0; i < hp; i++) {
      fill(255, 0, 0); 
      ellipse(width*0.65+width*0.1*i, height*0.97, width/16, width/16);
    }
    textAlign(CENTER);
    fill(255);
    textFont(font[1]);
    text("Health", width*0.8, height*0.94);
  }

  boolean death() {
    if (hp<=0) {
      return true;
    } else {
      return false;
    }
  }
}
