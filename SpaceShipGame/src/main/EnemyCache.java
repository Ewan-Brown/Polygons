package main;

import java.awt.Color;
import java.awt.Point;
//import java.awt.Shape;
import java.util.Hashtable;

public class EnemyCache {

	private static Hashtable<String, Enemy> entityMap  = new Hashtable<String, Enemy>();
	public static String[] types = {"light","medium","kamikaze","sniper"};
	public static void loadCache(){
		Enemy light = new Enemy(0,0){{
			caliber = 5;
			MAX_COOLDOWN = 7;
			health = 20;
			speed = 0.02;
			turnSpeed = 3;
			turnAccuracy = 5;
			points = new Point[7];
			points[0] = new Point(24,12);
			points[1] = new Point(6,16);
			points[2] = new Point(16,24);
			points[3] = new Point(0,16);
			points[4] = new Point(0,8);
			points[5] = new Point(16,0);
			points[6] = new Point(8,8);
			turrets = new Point[1];
			turrets[0] = points[0];
			maxRadius = 12;
			tempPoints = new Point[points.length];
			for(int i = 0; i < points.length;i++){
				tempPoints[i] = new Point(0,0);
			}
			
		}};
		Enemy sniper = new Enemy(0,0){{
			barrelSpeed = 200;
			minDist = 500;
			maxDist = 1000;
			caliber = 100;
			MAX_COOLDOWN = 100;
			points = new Point[11];
			points[0] = new Point(0,0);
			points[1] = new Point(8,0);
			points[2] = new Point(8,4);
			points[3] = new Point(12,8);
			points[4] = new Point(12,16);
			points[5] = new Point(8,20);
			points[6] = new Point(24,20);
			points[7] = new Point(24,24);
			points[8] = new Point(8,24);
			points[9] = new Point(4,28);
			points[10] = new Point(0,28);

			turrets = new Point[1];
			turrets[0] = points[8];
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
			public void onDeath(){
				Bullet[] b = GameMath.bulletRain(x, y, 5, team, 40, 10,c);
				gameInstance.addBullets(b);
				super.onDeath();
			}
			{
			health = 20;
			maxDist = 0;
			minDist = 0;
			speed = 0.03;
			turnSpeed = 1;
			turnAccuracy = 3;
			thrustAccuracy = 50;
			points = new Point[7];
			points[0] = new Point(24,12);
			points[1] = new Point(6,16);
			points[2] = new Point(16,24);
			points[3] = new Point(0,16);
			points[4] = new Point(0,8);
			points[5] = new Point(16,0);
			points[6] = new Point(8,8);
			turrets = new Point[0];
			maxRadius = 12;
			tempPoints = new Point[points.length];
			for(int i = 0; i < points.length;i++){
				tempPoints[i] = new Point(0,0);
			}
		}};
		Enemy medium = new Enemy(0,0){{
				caliber = 10;
				
				health = 50;
				MAX_COOLDOWN = 40;
				speed = 0.01;
				turnSpeed = 1;
				strafeChance = 0;
				minDist = 100;
				maxDist = 310;
				points = new Point[12];
				points[0] = new Point(20,4);
				points[1] = new Point(8,6);
				points[2] = new Point(8,10);
				points[3] = new Point(12,12);
				points[4] = new Point(12,20);
				points[5] = new Point(8,22);
				points[6] = new Point(8,26);
				points[7] = new Point(20,28);
				points[8] = new Point(4,32);
				points[9] = new Point(0,28);
				points[10] = new Point(0,4);
				points[11] = new Point(4,0);
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
		entityMap.put("sniper", sniper);



	}
	public static Enemy getEntity(String id) {
		Enemy cachedEntity = entityMap.get(id);
		return (Enemy) cachedEntity.clone();
	}

}
