package main;

import java.awt.Point;

public class Bullet extends Entity {
	{
		points = new Point[4];
		points[0] = new Point(10,2);
		points[1] = new Point(2,4);
		points[2] = new Point(0,2);
		points[3] = new Point(2,0);
		speed = 0.1;
		maxRadius = 10;
		tempPoints = new Point[points.length];
		for(int i = 0; i < points.length;i++){
			tempPoints[i] = new Point(0,0);
		}
	}
	
	public Bullet(double x, double y,double angle,int team){
		super(x,y,0,0);
		this.realAngle = angle;
		this.team = team;
		thrust(40);
	}
	public void update(){
		super.update();
	}
	public void frictionify(){
		
	}
	
}
