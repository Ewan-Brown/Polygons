package main;

import java.awt.Color;
import java.util.ArrayList;

public class Enemy extends Ship{

	Entity target;
	int strafeCount = 100;
	double strafe = 1;
	int maxDist = 120;
	int minDist = 100;
	double strafeChance = 50;
	double turnAccuracy = 20;
	double shotAccuracy = 1;
	double thrustAccuracy = 20;
	{
		health = 2;
		MAX_COOLDOWN = 1000;
	}

	public Enemy(double x, double y) {
		super(0,0);
		team = 1;
	}

	public void update(){
		super.update();
		updateTarget(gameInstance.getTargets());
		updateAngle();
		turnToTarget();
		moveToTarget();
	}

	public void moveToTarget(){
		double diffAngle = Math.abs((targetAngle - realAngle) % 360);
		if(diffAngle > 180){
			diffAngle -= 360;
		}
		if(Math.abs(diffAngle)  < thrustAccuracy){
			double d = GameMath.getDistance(this, target);
			double tempStrafe = (rand.nextDouble() - 0.5) / 4;
			if(d > maxDist){
				thrust(1);
				strafe(tempStrafe * 5);
			}
			else if(d < minDist){
				shoot();
				thrust(-1);
				strafe(tempStrafe * 2);
			}
			else{
				if(diffAngle < shotAccuracy){
					shoot();
				}
				strafeCount--;
				if(strafeCount > 0){
					strafe(strafe);
				}
				else if(rand.nextInt() < strafeChance){
					strafeCount = rand.nextInt(100);
					if(rand.nextBoolean()){
						strafe = -1;
					}
					else{
						strafe = 1;
					}
				}
			}
		}
	}
	public void turnToTarget(){
		double a = targetAngle - realAngle;
		a = a % 360;
		double diffAngle = Math.abs((targetAngle - realAngle) % 360);
		if(diffAngle < turnAccuracy){
			return;
		}
		if(a < 0){
			a += 360;
		}
		if(a < 180){
			turnRight();
		}
		else if (a > 180){
			turnLeft();
		}
	}
	public void updateTarget(ArrayList<Entity> array){
//		target = array.get(0);
		double dist = 0;
		double prevDist = 999999999;
		for(int i = 0; i < array.size();i++){
			if(array.get(i).team != this.team || !(array.get(i).dead)){
				dist = GameMath.getDistance(this, array.get(i));
				if(dist < prevDist){
					target = array.get(i);
					prevDist = dist;
				}
			}
		}
	}
	public void updateAngle(){
		double xD = target.getX() - getX();
		double yD = target.getY() - getY() ;
		targetAngle = Math.toDegrees(Math.atan2(yD, xD));
	}


}
