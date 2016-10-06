package main;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;



/**
 * Extendable class used to create polygon entities.
 * <p>
 * Make sure that the entity, at angle 0 is pointing upwards on screen, and declare all points IN ORDER.
 *
 */

public class Entity extends AbstractShip{
	
	public Point[] points;
	Game gameInstance;
	Point[] tempPoints;
	double x = 0;
	double y = 0;
	Color c = Color.RED;
	double dX = 0;
	double dY = 0;
	public double realAngle = 0;
	public double targetAngle = 0;
	double speed = 0.01;
	double turnSpeed = 1;
	Random rand = new Random();
	int health = 1;
	boolean dead = false;
	double maxRadius = 0;
	int team = 0;
	
	public Entity(double x, double y,double dX,double dY){
		this.x = x;
		this.y = y;
		this.dX = dX;
		this.dY = dY;
		gameInstance = Game.game;
	}
	public void damage(int d){
		this.health -= d;
		if(health < 0){
			onDeath();
		}
	}
	public void onDeath(){
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		c = new Color(r,g,b,80);
		dead = true;
		ArrayList<Particle> a = new ArrayList<Particle>();
		for(int i = 0; i < 10;i++){
			Particle p = new Particle(this.getX(),this.getY(),(rand.nextDouble() - 0.5) * 2,(rand.nextDouble() - 0.5) * 2);
			a.add(p);
		}
		gameInstance.addParticles(a);
	}
	public void onCollide(Entity e){
		
	}
	public Polygon getRotatedPolygon(){

		rotatePointMatrix(points,realAngle,tempPoints);
		return getPolygon(tempPoints);

	}
//	public Point2D getProperCenter(){
//		Point2D p = getCenter();
//		double x1 = p.getX() + x;
//		double y1 = p.getY() + y;
//		return new Point2D.Double(x1,y1);
//	}
	public double getX(){
		return x + getCenter().getX();
	}
	public double getTrueX(){
		return x;
	}
	public double getY(){
		return y + getCenter().getY();
	}
	public double getTrueY(){
		return y;
	}
	public Polygon getPolygon(Point[] p){

		Point[] p2 = updatePoints(p);

		Polygon tempPoly = new Polygon();

		for(int  i=0; i < p2.length; i++){
			tempPoly.addPoint(p2[i].x, p2[i].y);
		}

		return tempPoly;
	}
	public void update(){
		move();
		frictionify();
	}
	public void frictionify(){
		dX -= dX / 100;
		dY -= dY / 100;
	}
	public void move(){
		x += dX;
		y += dY;
	}
	public void thrust(double t){
		//TODO have to subtract 90 degrees here, why???
		double dX = (Math.cos(Math.toRadians(realAngle)))*speed*t;
		double dY = (Math.sin(Math.toRadians(realAngle)))*speed*t;
		this.dX += dX;
		this.dY += dY;
	}
	public void strafe(double t){
		double dX = (Math.cos(Math.toRadians(realAngle + 90)))*speed*t;
		double dY = (Math.sin(Math.toRadians(realAngle + 90)))*speed*t;
		this.dX += dX;
		this.dY += dY;
	}
	public void setPosition(double x, double y){
		this.x = x;
		this.y = y;
	}
	public Polygon getPolygon(){

		//a simple method that makes a new polygon out of the rotated points
		Point[] points2 = updatePoints(points);
		Polygon tempPoly = new Polygon();

		for(int  i=0; i < points2.length; i++){
			tempPoly.addPoint(points2[i].x, points2[i].y);
		}

		return tempPoly;
	}
	public void rotatePointMatrix(Point2D[] origPoints, double angle, Point2D[] storeTo){

		/* We ge the original points of the polygon we wish to rotate
		 *  and rotate them with affine transform to the given angle. 
		 *  After the operation is complete the points are stored to the 
		 *  array given to the method.
		 */
		Point2D p = getCenter();
		AffineTransform.getRotateInstance
		(Math.toRadians(angle), p.getX(), p.getY())
		.transform(origPoints,0,storeTo,0,points.length);

	}
	public Point[] updatePoints(Point[] p){
		Point[] newPoints = new Point[p.length];
		for(int i = 0; i < p.length;i++){
			newPoints[i] = new Point((int)(p[i].x + x),(int)(p[i].y + y));
		}
		return newPoints;
	}
	public void turnLeft(){
		realAngle -= turnSpeed;
	}
	public void turnRight(){
		realAngle += turnSpeed;
	}
	public Point2D getCenter(){
		double centreX = 0;
		double centreY = 0;
		double signedArea = 0.0;
		double x0 = 0.0; // Current vertex X
		double y0 = 0.0; // Current vertex Y
		double x1 = 0.0; // Next vertex X
		double y1 = 0.0; // Next vertex Y
		double a = 0.0;  // Partial signed area

		// For all points except last
		int i=0;
		for (i=0; i<points.length-1; ++i)
		{
			x0 = points[i].x;
			y0 = points[i].y;
			x1 = points[i+1].x;
			y1 = points[i+1].y;
			a = x0*y1 - x1*y0;
			signedArea += a;
			centreX += (x0 + x1)*a;
			centreY += (y0 + y1)*a;
		}

		// Do last vertex separately to avoid performing an expensive
		// modulus operation in each iteration.
		x0 = points[i].x;
		y0 = points[i].y;
		x1 = points[0].x;
		y1 = points[0].y;
		a = x0*y1 - x1*y0;
		signedArea += a;
		centreX += (x0 + x1)*a;
		centreY += (y0 + y1)*a;

		signedArea *= 0.5;
		centreX /= (6.0*signedArea);
		centreY /= (6.0*signedArea);
		return new Point2D.Double(centreX, centreY);

	}

}
