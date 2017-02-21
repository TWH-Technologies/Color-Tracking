/** 
 * Color Tracking 1.1
 * by TWH Technologies on November 23, 2016
 * 
 * https://twhtechnologies.com/color-tracking
 */

/**
 * No Repeat ID Input (v1.10)
 * by GoToLoop (2013/Nov)
 * 
 * forum.processing.org/two/discussion/869
 * /check-array-contents-with-arraylist
 */

//Import the video library to be able to use your video camera
import processing.video.*;
import static javax.swing.JOptionPane.*;
//Create a snake class associated with the snake var
Snake snake;
//Create a video var
Capture video;
//Var for the color to track
color trackColor;
void setup() {
  showMessageDialog(null, "To select a color to track simply click on the color with your mouse.", "Color Tracking v1.1", INFORMATION_MESSAGE); 
  //Any size will work
  size(1280, 720);
  //Set some default parameters for the sketch
  snake = new Snake(255, 0, 0, 90, "ellipse");
  video = new Capture(this, width, height);
  video.start();
  trackColor = color(255, 0, 0);
}
void captureEvent(Capture video) {
  video.read();
}
void mousePressed() {
  int loc = mouseX + mouseY*video.width;
  trackColor = video.pixels[loc];
}
void draw() {
  video.loadPixels();
  image(video, 0, 0);
  float record = 500;
  int closestX = 0;
  int closestY = 0;
  for (int x = 0; x < video.width; x++) {
    for (int y = 0; y < video.height; y++) {
      int loc = x + y*video.width;
      color currentColor = video.pixels[loc];
      float r1 = red(currentColor);
      float g1 = green(currentColor);
      float b1 = blue(currentColor);
      float r2 = red(trackColor);
      float g2 = green(trackColor);
      float b2 = blue(trackColor);
      float d = dist(r1, g1, b1, r2, g2, b2);
      if (d < record) {
        record = d;
        closestX = x;
        closestY = y;
      }
    }
  }
  if (record < 10) {
    fill(trackColor);
    strokeWeight(4.0);
    stroke(0);
    ellipse(closestX, closestY, 16, 16);
  }
  snake.display(red(trackColor), green(trackColor), blue(trackColor), 45, "ellipse", closestX, closestY);
}