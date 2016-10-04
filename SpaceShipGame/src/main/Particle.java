package main;

import java.awt.Color;
import java.awt.Point;

public class Particle extends Entity{

	{
		points = new Point[4];
		points[0] = new Point(0,0);
		points[1] = new Point(2,0);
		points[2] = new Point(2,2);
		points[3] = new Point(0,2);
		tempPoints = new Point[4];
		for(int i = 0 ; i < tempPoints.length;i++){
			tempPoints[i] = new Point(0,0);
		}
	}
	
	public Particle(double x, double y, double dX, double dY) {
		super(x, y, dX, dY);
		c = Color.GREEN;
	}
	public void update(){
		super.update();
		if(dX < 0.1 && dY < 0.1){
			onDeath();
		}
	}
	public void onDeath(){
		this.dead = true;
	}

}
