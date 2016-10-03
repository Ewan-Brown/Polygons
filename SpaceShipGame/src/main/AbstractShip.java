package main;

import java.awt.Point;

public abstract class AbstractShip implements Cloneable{

	public Point[] points;
	public double speed;

	public Point[] getPoints(){
		return points;
	}

	public Object clone() {
		Object clone = null;

		try {
			clone = super.clone();

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return clone;
	}

}
