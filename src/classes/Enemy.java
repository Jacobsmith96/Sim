package classes;

//Jacob Smith
public class Enemy extends Point{
	public int speed;
	public Enemy(){ //Default constructor, makes an enemy at a random angle around the middle circle
		fallingSpeed = 3;
		angle = (int)(Math.random()*360);
		midX=(int)(Math.cos(angle/57.2957795)*(600))+400;
		xVal=midX-10;
		midY=(int)(Math.sin(angle/57.2957795)*(600))+400;
		yVal=midY-10;
		r = 10;
		speed = 1;
	}
	public void moveClock(){ //Moves enemy clockwise
		angle-=speed;
		midX=(int)(Math.cos(angle/57.2957795)*(150+getRadius()))+400;
		xVal=midX-getRadius();
		midY=(int)(Math.sin(angle/57.2957795)*(150+getRadius()))+400;
		yVal=midY-getRadius();
		movingClock=true;
		movingCounter=false;
	}
	public void moveCounter(){ //Moves enemy counter clockwise
		angle+=speed;
		midX=(int)(Math.cos(angle/57.2957795)*(150+getRadius()))+400;
		xVal=midX-getRadius();
		midY=(int)(Math.sin(angle/57.2957795)*(150+getRadius()))+400;
		yVal=midY-getRadius();
		movingClock=false;
		movingCounter=true;
	}
}
