import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.video.*; 
import static javax.swing.JOptionPane.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Color_Tracking extends PApplet {

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


//Create a snake class associated with the snake var
Snake snake;
//Create a video var
Capture video;
//Var for the color to track
int trackColor;
public void setup() {
  showMessageDialog(null, "To select a color to track simply click on the color with your mouse.", "Color Tracking v1.1", INFORMATION_MESSAGE); 
  //Any size will work
  
  //Set some default parameters for the sketch
  snake = new Snake(255, 0, 0, 90, "ellipse");
  video = new Capture(this, width, height);
  video.start();
  trackColor = color(255, 0, 0);
}
public void captureEvent(Capture video) {
  video.read();
}
public void mousePressed() {
  int loc = mouseX + mouseY*video.width;
  trackColor = video.pixels[loc];
}
public void draw() {
  video.loadPixels();
  image(video, 0, 0);
  float record = 500;
  int closestX = 0;
  int closestY = 0;
  for (int x = 0; x < video.width; x++) {
    for (int y = 0; y < video.height; y++) {
      int loc = x + y*video.width;
      int currentColor = video.pixels[loc];
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
    strokeWeight(4.0f);
    stroke(0);
    ellipse(closestX, closestY, 16, 16);
  }
  snake.display(red(trackColor), green(trackColor), blue(trackColor), 45, "ellipse", closestX, closestY);
}

class Snake {
  int[] xpos = new int[30];
  int[] ypos = new int[30];
  int r, g, b, s;
  String shape;
  Snake(int rt, int gt, int bt, int st, String shapet) {
    for (int i = 0; i < xpos.length; i++) {
      xpos[i] = 0;
      ypos[i] = 0;
      /*r = rt;
       g = gt;
       b = bt;
       s = st;
       shape = shapet;*/
    }
  }

  public void display(float r, float g, float b, int s, String shape,int x1, int y1) {
    for (int i = 0; i < xpos.length - 1; i++) {
      xpos[i] = xpos[i + 1];
      ypos[i] = ypos[i + 1];
    }

    xpos[xpos.length - 1] = x1;
    ypos[ypos.length - 1] = y1;

    for (int i = 0; i < xpos.length; i++) {
      noStroke();
      rectMode(CENTER);
      fill(r - i * 5, g - i * 5, b - i * 5);
      if (shape == "ellipse") {
        ellipse(xpos[i], ypos[i], s, s);
      } else if (shape == "square") {
        rect(xpos[i], ypos[i], s, s);
      } else if (shape == "longEllipse") {
        ellipse(xpos[i], ypos[i], s * 1.5f, s);
      }
    }
  }
}
  public void settings() {  size(1280, 720); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Color_Tracking" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
