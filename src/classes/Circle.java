//Jacob Smith
package classes;
import java.awt.Graphics;
public class Circle{
	public int xVal; //xpos
	public int yVal; //ypos
	public int r; //radius	
	public int midX; //midpoint x
	public int midY; //midpoint y
	public int angle = 0; //Angle of mid point of the circle relative to the middle circle
	public int dist=1; // Distance from center of middle circle
	public boolean jumping = false; //These booleans track what the circle is doing
	public boolean falling = false;
	public boolean movingClock = false;
	public boolean movingCounter = false;
	public boolean canJump = true;
	public Circle(){ //Defualt constructor
		xVal = 0;
		yVal = 0;
		r = 50;
		midX = 25;
		midY = 25;
	}
	public Circle(int x, int y, int rad){ //Standard constructor
		xVal = x;
		yVal = y;
		r = rad;
		midX = x+rad;
		midY = y+rad;
	}
	public Circle(String y, int rad){ //For middle circle only
		if(y.equals(y))
		xVal = 400-150;
		yVal = 400-150;
		r=rad;
		midX = 400;
		midY = 400;
	}
	//Accessor methods
	public int getX(){
		return xVal;
	}
	public int getY(){
		return yVal;
	}
	public int getRadius(){
		return r;
	}
	public int getMidX(){
		return midX;
	}
	public int getMidY(){
		return midY;
	}
	public int getAngle(){
		return angle;
	}
	public void paintCircle(Graphics g){ //Paints a circle based on radius and x/y
		g.drawOval(getX(),getY(),2*getRadius(),2*getRadius());
	}
	public void paintPlayer(Graphics g){ //Paints a circle based on midpoint and radius
		g.drawOval(getMidX()-25,getMidY()-25,2*getRadius(),2*getRadius());
	}
	public void paintEnemy(Graphics g){ //Paints enemies
		g.drawOval(getMidX()-10,getMidY()-10,2*getRadius(),2*getRadius());
	}
	public void moveClock(){ //Moves the circle by 2 degrees around the middle circle clockwise
		angle-=2;
		midX=(int)(Math.cos(Math.toRadians(angle))*(150+getRadius()+dist))+400;
		xVal=midX-getRadius();
		midY=(int)(Math.sin(Math.toRadians(angle))*(150+getRadius()+dist))+400;
		yVal=midY-getRadius();
	}
	public void moveCounter(){ //Moves the circle by 2 degrees around the middle circle counter Clockwise
		angle+=2;
		midX=(int)(Math.cos(Math.toRadians(angle))*(150+getRadius()+dist))+400;
		xVal=midX-getRadius();
		midY=(int)(Math.sin(Math.toRadians(angle))*(150+getRadius()+dist))+400;
		yVal=midY-getRadius();
	}
	public void jumping(){ //Increases the circle's distance from the middle circle
		dist+=3;
		midX=(int)(Math.cos(Math.toRadians(angle))*(150+getRadius()+dist))+400;
		xVal=midX-25;
		midY=(int)(Math.sin(Math.toRadians(angle))*(150+getRadius()+dist))+400;
		yVal=midY-25;
	}
	public void falling(){ //Decreases the circle's distance from the middle circle
		if(dist>1)
		dist-=3;
		midX=(int)(Math.cos(Math.toRadians(angle))*(150+getRadius()+dist))+400;
		xVal=midX-25;
		midY=(int)(Math.sin(Math.toRadians(angle))*(150+getRadius()+dist))+400;
		yVal=midY-25;
	}
	public void update(){ //Updates the player's position based upon it's booleans
		if(movingClock){
			moveClock();
		}
		else if(movingCounter){
			moveCounter();
		}
		if(jumping){
			jumping();
		}
		else if(falling){
			falling();
		}
		if(dist <2){
			falling = false;
			jumping = false;
			canJump = true;
		}
		else if(dist>50){
			falling = true;
			jumping = false;
			canJump = false;
		}
		if(angle<0)
			angle = 360 - Math.abs(angle); 
		else if(angle>360)
			angle = angle%360;
	}
	public boolean collision(Circle circ1){ //Checks for collision between two circles
		
		double a = getRadius()+circ1.getRadius();
		double dx = (getMidX() - circ1.getMidX());
		double dy = (getMidY() - circ1.getMidY());
		return a * a > ((dx*dx) + (dy*dy));
	}
}
