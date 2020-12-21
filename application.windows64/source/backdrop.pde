class Backdrop {
  int stars=100;
  float[] starx= new float[stars];
  float[] stary= new float[stars];
  float x;
  float dia=0;
  float filltime=0;
  float time=0;
  PVector velocity;

  void render() {
    rectMode(CORNER);
    noStroke();
    for (int i=0; i < 81; i++) {
      float h=height/8;
      fill(15+0.2*i, 20+0.2*i, 40+0.75*i);
      rect(0, 0+0.1*h*i, width, 0.1*h);
    }
    for (int i=0; i < stars; i++) {
      fill(255); 
      ellipse(starx[i], stary[i], 5, 5);
    }
  }


  void generatestars() {
    for (int i=0; i < stars; i++) {
      starx[i]=random(width);
      stary[i]=random(height);
    }
  }

  void shake() {
    for (int i=0; i < stars; i++) {
      starx[i]=0;
      stary[i]=0;
    }
  }

  void explodeCoords(float xTemp) {
    x=xTemp+width/2;
  }
  
  void explode() {
    noStroke();
    fill(255, 0, 0);
    ellipse(x, height*0.9, dia*2, dia*2);
    fill(255, 140, 0);
    ellipse(x, height*0.9, dia*1.5, dia*1.5);
    fill(255, 215-filltime, 0);
    ellipse(x, height*0.9, dia, dia);
    
    if (time>0 && time<20) {
      dia=dia+10*ts;
    } else if (time>20 && time<30) {
      dia=dia-20*ts;
    } else if (time>38) {
      if (dia<width) dia=dia+10*ts*2;
      if (dia>width) dia=dia+5*ts*2;
      if (dia>height*1.5) {
        filltime=filltime+1;
      }
    }
    time+=1;
  }
  
  void aim(PVector aim1, PVector turretCenter1){
    
    velocity=aim1;
    velocity.sub(turretCenter1);
    velocity.setMag(height/16);
  }
  void turret() {
    noStroke();
    fill(50);
    rect(-width/2, 0, width, height*0.1);
    fill(80);
    rectMode(CENTER);
    rect(0, 0+height*0.015, width*0.2, height*0.05);


    stroke(200);
    strokeWeight(5*ts);

    line(velocity.x-velocity.x+velocity.y/5, velocity.y-velocity.y-velocity.x/5, velocity.x+velocity.y/5, velocity.y-velocity.x/5);
    line(velocity.x-velocity.x-velocity.y/5, velocity.y-velocity.y+velocity.x/5, velocity.x-velocity.y/5, velocity.y+velocity.x/5);


    noStroke();
    fill(120);
    ellipse(0, 0, height/20, height/20);
  }
}
