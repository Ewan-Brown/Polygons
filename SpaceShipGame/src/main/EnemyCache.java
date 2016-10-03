package main;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.util.Hashtable;

public class EnemyCache {

	private static Hashtable<String, Enemy> entityMap  = new Hashtable<String, Enemy>();
	public static String[] types = {"light","medium","kamikaze"};
	public static void loadCache(){
		Enemy light = new Enemy(0,0){{
			MAX_COOLDOWN = 20;
			health = 20;
			c = Color.CYAN;
			speed = 0.02;
			turnSpeed = 3;
			points = new Point[7];
			points[0] = new Point(12,6);
			points[1] = new Point(4,8);
			points[2] = new Point(8,12);
			points[3] = new Point(0,8);
			points[4] = new Point(0,4);
			points[5] = new Point(8,0);
			points[6] = new Point(4,4);
			turrets = new Point[1];
			turrets[0] = points[0];
			maxRadius = 12;
			tempPoints = new Point[points.length];
			for(int i = 0; i < points.length;i++){
				tempPoints[i] = new Point(0,0);
			}
			
		}};
		Enemy kamikaze = new Enemy(0,0){
			public void onCollide(Entity e){
				if(e.team != this.team){
					e.damage(50);
					onDeath();
				}
			}
			{
			health = 1;
			c = Color.GRAY;
			maxDist = 0;
			minDist = 0;
			speed = 0.03;
			turnSpeed = 1;
			turnAccuracy = 1;
			thrustAccuracy = 50;
			points = new Point[7];
			points[0] = new Point(12,6);
			points[1] = new Point(4,8);
			points[2] = new Point(8,12);
			points[3] = new Point(0,8);
			points[4] = new Point(0,4);
			points[5] = new Point(8,0);
			points[6] = new Point(4,4);
			turrets = new Point[0];
			maxRadius = 12;
			tempPoints = new Point[points.length];
			for(int i = 0; i < points.length;i++){
				tempPoints[i] = new Point(0,0);
			}
		}};
		Enemy medium = new Enemy(0,0){{
				c = Color.BLUE;
				health = 50;
				MAX_COOLDOWN = 40;
				speed = 0.01;
				turnSpeed = 1;
				strafeChance = 0;
				minDist = 100;
				maxDist = 310;
				points = new Point[12];
				points[0] = new Point(10,2);
				points[1] = new Point(4,3);
				points[2] = new Point(4,5);
				points[3] = new Point(6,6);
				points[4] = new Point(6,10);
				points[5] = new Point(4,11);
				points[6] = new Point(4,13);
				points[7] = new Point(10,14);
				points[8] = new Point(2,16);
				points[9] = new Point(0,14);
				points[10] = new Point(0,2);
				points[11] = new Point(2,0);
				turrets = new Point[2];
				turrets[0] = points[0];
				turrets[1] = points[7];
				maxRadius = 16;
				tempPoints = new Point[points.length];
				for(int i = 0; i < points.length;i++){
					tempPoints[i] = new Point(0,0);
				}
			}
		};
		entityMap.put("light", light);
		entityMap.put("medium", medium);		
		entityMap.put("kamikaze", kamikaze);



	}
	public static Enemy getEntity(String id) {
		Enemy cachedEntity = entityMap.get(id);
		return (Enemy) cachedEntity.clone();
	}

}
