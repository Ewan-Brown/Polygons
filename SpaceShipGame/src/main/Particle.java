package main;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

public class Particle extends Entity{
	double spin = 0;
	public static Random rand = new Random();
	
	public Particle(double x, double y, double dX, double dY) {
		super(x, y, dX, dY);
		c = Color.GREEN;
		spin = (rand.nextDouble() - 0.5) * 5;
		int a = rand.nextInt(4);
		points = new Point[4];
		points[0] = new Point(0,0);
		points[1] = new Point(a,0);
		points[2] = new Point(a,a);
		points[3] = new Point(0,a);
		tempPoints = new Point[4];
		for(int i = 0 ; i < tempPoints.length;i++){
			tempPoints[i] = new Point(0,0);
		}
	}
	public void update(){
		super.update();
		realAngle += spin;
		if(dX < 0.1 && dY < 0.1){
			onDeath();
		}
	}
	public void onDeath(){
		this.dead = true;
	}

}
