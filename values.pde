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

void init() {
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
