//Jacob Smith
package classes;

public class Point extends Circle{
	
	public int fallingSpeed;
	public int angle;
	public int dist;
	public int type;
	public Point(){
		dist = 600;
		angle = (int)(Math.random()*360);
		type = (int)(Math.random()*3); //Picks a type for the point (0, 1, or 2)
		if (type == 0){
			r =20;
		}
		else if(type == 1){
			r =15;
		}
		else if(type == 2){
			r = 10;
		}
		midX=(int)(Math.cos(angle/57.2957795)*(dist))+400;
		xVal=midX-r;
		midY=(int)(Math.sin(angle/57.2957795)*(dist))+400;
		yVal=midY-r;
		fallingSpeed = type+1;
		
	}
	public void fall(){ //Makes the point fall towards the center circle
			dist-=fallingSpeed;
			midX=(int)(Math.cos(angle/57.2957795)*(dist))+400;
			xVal=midX-r;
			midY=(int)(Math.sin(angle/57.2957795)*(dist))+400;
			yVal=midY-r;
			
	}
	public int getType(){
		return type;
	}
}
