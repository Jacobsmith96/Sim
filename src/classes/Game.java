//Jacob Smith
package classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;  
import javax.swing.WindowConstants;


public class Game extends JFrame implements ActionListener, KeyListener
{
	public BufferedImage myImage;
	public Graphics2D myBuffer;
	boolean running; //States whether the game is still running or not
	Circle circ1; //Middle circle of the game
	Circle player; //Player controlled circle
	JPanel panel; //Background panel
	Timer timer; //Initializes the timer
	public int tick = 0;//Tick of the timer
	ArrayList keys = new ArrayList(); //ArrayList that tracks currently pressed keys
	ArrayList<Point> points = new ArrayList<Point>();//Tracks point objects currently in existence
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();//Tracks enemy objects currently in existence
	public int score = 0;//Player's score
	public int enemyCount = 0;//Number of Enemies
	//Default constructor for game called by Driver
	public Game(){
		myImage =  new BufferedImage(800,800,BufferedImage.TYPE_INT_RGB);//Creates image for double buffering
	    myBuffer = myImage.createGraphics();//Gets the graphics from the new image
		running = false; //Sets the game to running
		circ1 = new Circle("y",150); //Instantiates the Middle circle
		player = new Circle(550,375,25); //Instantiates the player controlled circle
		timer = new Timer(20, this); //Creates a new timer
		addKeyListener(this);//Sets up the key listeners
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Program terminates on exit
		
	}
	//Starts running the game by setting the running boolean to true and starting the timer
	public void run(){
		running = true;
		timer.start();
	}
	//Paints every tick of the timer and draws the double buffering image all at once to prevent
	//flashing of the graphics
	public void paint(Graphics g)
	{
		g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
	}
	//Updates all necessary components every tick of the timer
	public void actionPerformed(ActionEvent e){
		//Checks and performs behavior for every enemy
		for(Enemy a : enemies){ //Makes the enemies fall down until they hit the middle circle
			if(a.dist>160){
				a.fall();
			}
			else{ //Decides which direction the enemies will move in and at what speed based on
				  //their index
				if(enemies.indexOf(a)==2){
					a.speed = 2;
					a.moveClock();
				}
				if(enemies.indexOf(a)==0)
					a.moveClock();
				else if(enemies.indexOf(a)==1)
					a.moveCounter();
				
			}
			if(player.collision(a)){die();}//Kills the player and ends the game
										   //on collision with an enemy
			
		}
		//Checks and performs behavior for every Point object
		for(int a = 0;a<points.size();a++){
			//Subtracts one point from score and removes the Point object if it reaches the middle circle
			if(points.get(a).dist<150 + points.get(a).getRadius()){ 
				points.remove(a);
				if(score>0)
				score--;
			}
			//Adds the correct value to score based on the type of Point object and adds enemies based on score
			if(player.collision(points.get(a)))
			{
				if(points.get(a).getType()==0){
					score++;
				}
				else if(points.get(a).getType()==1){
					score+=2;
				} 
				else if(points.get(a).getType()==2){
					score+=3;
				}
				points.remove(a);
				a--;
				if(score>4&&enemyCount<1){
					enemyCount++;
				}
				else if(score>9&&enemyCount<2){
					enemyCount++;
				}
				else if(score>14&&enemyCount<3){
					enemyCount++;
				}
			}
			else //Point objects fall until they reach the ground
			points.get(a).fall();
			
		}
		//Adds enemies based on the enemyCount integer value
		if(enemies.size()<enemyCount){
			enemies.add(new Enemy());
		}
		player.update(); //Updates the player's position and indicator booleans 
		paintObjects(); //Uses the buffered image to paint objects to
		repaint(); //Repaints the buffered image
		if(tick%99==0){ //Adds a new point every 100 ticks
			points.add(new Point());
		}
		if(tick<1000){ //Increments ticks up to 1000
			tick++;
		}
		else //Resets ticks to 0
			tick = 0;
	}
	public void create(){ //Sets up the JFrame and calls run() to start the program
		setSize(800,800);
		repaint();
		show();
		run();
	}
	public void paintObjects(){ //Handles painting all the objects to the buffered image
		if(running){
			myBuffer.setColor(Color.BLACK);
			myBuffer.fillRect(0, 0, 800, 800);
			myBuffer.setColor(Color.BLUE);
			circ1.paintCircle(myBuffer);
			myBuffer.setColor(Color.YELLOW);
			player.paintPlayer(myBuffer);
			myBuffer.setColor(Color.RED);
			for(Enemy a : enemies){
				a.paintEnemy(myBuffer);
			}
			for(Point a : points){
				if(a.type==0){
					myBuffer.setColor(Color.GREEN);
					a.paintCircle(myBuffer);
				}
				else if(a.type==1){
					myBuffer.setColor(Color.BLUE);
					a.paintCircle(myBuffer);
				}
				else if(a.type==2){
					myBuffer.setColor(Color.WHITE);
					a.paintCircle(myBuffer);
				}
			}
			myBuffer.setColor(Color.WHITE);
			myBuffer.setFont(new Font("Arial", Font.PLAIN, 30));
			myBuffer.drawString("Score:" + score, 10,60);
		}
		else{
			myBuffer.setColor(Color.BLACK);
			myBuffer.fillRect(0, 0, 800, 800);
			myBuffer.setColor(Color.BLUE);
			circ1.paintCircle(myBuffer);
			myBuffer.setColor(Color.YELLOW);
			player.paintPlayer(myBuffer);
			myBuffer.setColor(Color.RED);
			for(Enemy a : enemies){
				a.paintEnemy(myBuffer);
			}
			myBuffer.setColor(Color.GREEN);
			for(Point a : points){
				if(a.type==0){
					myBuffer.setColor(Color.GREEN);
					a.paintCircle(myBuffer);
				}
				else if(a.type==1){
					myBuffer.setColor(Color.BLUE);
					a.paintCircle(myBuffer);
				}
				else if(a.type==2){
					myBuffer.setColor(Color.WHITE);
					a.paintCircle(myBuffer);
				}
			}
			myBuffer.setColor(Color.WHITE);
			myBuffer.setFont(new Font("Arial", Font.PLAIN, 20));
			myBuffer.drawString("GAME OVER", 340,400);
			myBuffer.drawString("Your Score: " + score, 340,425);
			myBuffer.drawString("Play Again? Y/N", 340,450);
		}
		//g.drawLine(circ1.getMidX(),circ1.getMidY(),player.getMidX(),player.getMidY());
	}
	public void keyPressed(KeyEvent e) {//Updates every time a key is pressed
		int key = e.getKeyCode();
		if(!keys.contains(key))
			keys.add(key); //Adds the newly pressed key to the keys arrayList
		if(keys.contains(KeyEvent.VK_SPACE)&&player.canJump){ //Sets jumping boolean to true if
			                                                  //spacebar is pressed
			player.jumping = true;
		}
		if(keys.contains(KeyEvent.VK_RIGHT)){ //Sets movingCounter to true if right is pressed
			player.movingCounter=true;
		}
		if(keys.contains(KeyEvent.VK_LEFT)){ //Sets movingClock to true if left is pressed
			player.movingClock=true;
		}
		if(keys.contains(KeyEvent.VK_Y)){
			if(!running){
				reset();
			}
		}
		if(keys.contains(KeyEvent.VK_N)){
			if(!running){
			System.exit(0);
			}
		}
	}

	public void keyReleased(KeyEvent e) {//Updates keys that are released
		int key = e.getKeyCode();
		if(keys.size()>0)
		keys.remove(keys.indexOf(key)); //Removes the released key from the keys ArrayList
		if(key == KeyEvent.VK_SPACE){ //Stops the player from jumping and makes it so they
								      //can't jump again until they reach the ground
			player.jumping = false;
			player.canJump = false;
			player.falling= true;
		}
		if(key == KeyEvent.VK_RIGHT){
			player.movingCounter=false;
		}
		else if(key == KeyEvent.VK_LEFT){
			player.movingClock=false;
		}
	}
	public void keyTyped(KeyEvent e) {}
	public void die(){ //Stops the program on death and sets running to false, changing what is painted
		running = false;
		timer.stop();
		repaint();
		
	}
	public int getTick(){ //Returns the current Tick Value
		return tick;
	}
	public void reset(){ //Restarts the program if the player chooses to do so
		player = new Circle(550,375,25);
		score = 0;
		timer.start();
		running = true;
		points = new ArrayList<Point>();
		enemies = new ArrayList<Enemy>();
		enemyCount = 0;
		repaint();
	}
}



