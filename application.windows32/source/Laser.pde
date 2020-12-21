class Laser {

  PVector laser= new PVector(0, 0);
  PVector velocity;

  Laser(PVector aim, PVector turretCenter) {
    velocity=aim;
    velocity.sub(turretCenter);
    velocity.setMag(height/27);
  }

  void render() {
    //To check hitboxes
    //fill(255, 0, 0);
    //ellipse(laser.x-velocity.x*0.75, laser.y-velocity.y*0.75, cDia/2+height/54, cDia/2+height/54);
    strokeWeight(5*ts);
    stroke(100, 255, 100);
    line(laser.x-velocity.x*1.5+velocity.y/3, laser.y-velocity.y*1.5-velocity.x/3, laser.x+velocity.y/3, laser.y-velocity.x/3);
    line(laser.x-velocity.x*1.5-velocity.y/3, laser.y-velocity.y*1.5+velocity.x/3, laser.x-velocity.y/3, laser.y+velocity.x/3);
  }

  void shoot() {
    laser.add(velocity);
  }

  //Checks if Laser hits target
  boolean hit(float targetX, float targetY) {
    float d= dist(laser.x-velocity.x*0.75, laser.y-velocity.y*0.75, targetX, targetY);
    if ( d < cDia/2+height/54) {
      return true;
    } else {
      return false;
    }
  }
}
