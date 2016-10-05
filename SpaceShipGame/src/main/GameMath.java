package main;

import java.awt.Color;
import java.util.Random;

public class GameMath {

	public static double getDistance(double x1, double y1, double x2, double y2){
		return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	public static double getDistance(Entity e1,Entity e2){
		return getDistance(e1.x,e1.y,e2.x,e2.y);
	}
	public static Bullet[] bulletRain(double x, double y, int n,int team,double speed,int dmg,Color c){
		Bullet[] ba = new Bullet[n];
		Random rand = new Random();
		for(int i = 0; i < n;i++){
			ba[i] = new Bullet(x,y,rand.nextInt(360),team,speed,dmg,c);
		}
		return ba;
		
	}
	
}
