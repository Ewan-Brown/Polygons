package main;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Ship extends Entity{


	boolean playerControl = false;
	int cooldown = 0;
	int MAX_COOLDOWN = 10;
	double barrelSpeed = 40;
	Point[] turrets = new Point[0];

	{
		c = Color.GREEN;
		//		points = new Point[7];
		//		points[0] = new Point(20,0);
		//		points[1] = new Point(30,30);
		//		points[2] = new Point(40,40);
		//		points[3] = new Point(30,50);
		//		points[4] = new Point(10,50);
		//		points[5] = new Point(0,40);
		//		points[6] = new Point(10,30);
		points = new Point[7];
		points[0] = new Point(50,20);
		points[1] = new Point(20,30);
		points[2] = new Point(10,40);
		points[3] = new Point(0,30);
		points[4] = new Point(0,10);
		points[5] = new Point(10,0);
		points[6] = new Point(20,10);
		turrets = new Point[2];
		turrets[0] = new Point(points[0].x,points[0].y + 3);
		turrets[1] = new Point(points[0].x,points[0].y - 3);
		maxRadius = 50;
		tempPoints = new Point[points.length];
		for(int i = 0; i < points.length;i++){
			tempPoints[i] = new Point(0,0);
		}
	}

	public Ship(int x, int y){
		super(x,y,0,0);
		team = 2;
		health = 100000000;

	}
	public void update(){
		super.update();
		cooldown--;
	}	
	public void shoot(){
		Bullet[] bA = new Bullet[turrets.length];
		if(cooldown < 0){
			cooldown = MAX_COOLDOWN;
			Point[] p = getTurrets();
			for(int i = 0; i < p.length;i++){
				double x = p[i].x + getTrueX();
				double y = p[i].y + getTrueY();
				Bullet b = new Bullet(x,y,realAngle,this.team,barrelSpeed);
				b.c = this.c;
				bA[i] = b;
			}
			gameInstance.addBullets(bA);
		}
	}
	public Point[] getTurrets(){
		Point[] tempPoints = new Point[turrets.length];
		for(int i = 0; i < tempPoints.length;i++){
			tempPoints[i] = new Point(0,0);
		}
		Point2D p = getCenter();
		AffineTransform.getRotateInstance
		(Math.toRadians(realAngle), p.getX(), p.getY())
		.transform(turrets,0,tempPoints,0,turrets.length);
		return tempPoints;
	}


}
