void mousePressed() {
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
