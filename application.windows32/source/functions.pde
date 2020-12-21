void cometSpawn() {
  for (int i=0; i < difficulty; i++) {
    cometer.add(new Comet(cDia, graphics, random(minSpeed, maxSpeed)));
  }
}

void menuLayout() {
  rectMode(CENTER);

  fill(255);
  triangle(b/2-b*0.25, h/2.5-b*0.25, b/2-b*0.25, h/2.5+b*0.25, b/2+b*0.25, h/2.5);
  textSize(height/35);
  textAlign(CENTER);


  menuComet.y=h/2-h*0.3;
  menuComet.x=b/2+b*0.2;
  menuComet.render();

  fill(255);
  rect(b/4.5, h-h/15, b/4, b/80);
  ellipse(b/4.5-b/15, h-h/15, b/15, b/15);

  noStroke();
  translate(b/2+b*0.25, h/2+h*0.35);
  rotate(radians(45*3));

  rectMode(CENTER);
  fill(50);
  rect(0, -b/2.1, width*2, b);
  fill(80);
  rect(0, -b/30, b/4, b/6);

  fill(200);
  rect(-b/35, b/10, b/60, b/7.5);
  rect(b/35, b/10, b/60, b/7.5);
  fill(120);
  noStroke();
  ellipse(0, 0, b/7.5, b/7.5);  

  fill(100, 255, 100);
  rect(-b/35, b/3, b/70, b/11);
  rect(b/35, b/3, b/70, b/11);
  rect(-b/35, b/1.7, b/70, b/11);
  rect(b/35, b/1.7, b/70, b/11);
}

void tri(float x, float y) {
  triangle(x-width*0.2, y-width*0.2, x-width*0.2, y+width*0.2, x+width*0.2, y);
}

void configLayout() {
  textAlign(CENTER, CENTER);
  fill(255);
  textFont(font[0]);
  text("Difficulty "+difficulty, b/2, h*0.2);


  rectMode(CENTER);
  rect(b/2-b*0.1, h*0.5, b/15, b/3);
  rect(b/2+b*0.1, h*0.5, b/15, b/3);

  cHigh.x=width/4;
  cHigh.y=height*0.85;
  cHigh.render();
  textFont(font[1]);
  fill(255);
  text("Graphics: "+cHigh.graphics, width/4, height*0.9);

  text("Sounds: "+sound, width*0.75, height*0.9);
  pushMatrix();
  translate(width*0.75, height*0.85);
  rotate(radians(180));
  fill(200);
  rect(-b/35, b/10, b/60, b/7.5);
  rect(b/35, b/10, b/60, b/7.5);
  fill(120);
  noStroke();
  ellipse(0, 0, b/7.5, b/7.5);
  popMatrix();

  fill(255);
  rect(b/2, h*0.3, b/1.5, b/80);
  ellipse(configBall, h*0.3, b/15, b/15);
}
