package main;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements KeyListener,MouseListener,ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Ship ship;
	static Random rand = new Random();
	BitSet keySet = new BitSet(256);
	public static Game game;
	ArrayList<Ship> entities = new ArrayList<Ship>();
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	ArrayList<Particle> particleArray = new ArrayList<Particle>();
	boolean lockOn = true;
	int[] cooldown = new int[1];
	int x;
	int y;
	int minX = 0;
	int maxX = 3000;
	int minY = 0;
	int maxY = 3000;
	public Point p2 = new Point(0,0);
	Timer t;
	public static void main(String[] args){

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game = new Game();
		frame.add(game);
		frame.setSize(1920,1080);
		frame.setVisible(true);
		frame.addKeyListener(game);
		frame.addMouseListener(game);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH); 
		game.init();
		game.setBackground(Color.BLACK);
		while(true){
			game.doKeys();
			game.update();
			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	public Game(){}
	public void spawnEnemy(){
		Enemy e = randomEnemy();
		e.setPosition(rand.nextInt(getWidth()),rand.nextInt(getHeight()));
		entities.add(e);
	}
	public void init(){
		cooldown[0] = 100;
		t = new Timer(1000,this);
		t.start();
		//		ship = new Ship(0,0);
		int f = getWidth();
		int h = f / 2;
		int q = h / 2;
		EnemyCache.loadCache();
		int n = 20;
		for(int i = 0; i < n;i++){
			Enemy e = randomEnemy();
			e.setPosition(rand.nextInt(q),rand.nextInt(getHeight()));
			e.team = 2;
			e.c = Color.BLUE;
			entities.add(e);
		}
		for(int i = 0; i < n;i++){
			Enemy e = randomEnemy();
			e.setPosition(rand.nextInt(q) + h + q,rand.nextInt(getHeight()));
			e.team = 3;
			e.realAngle -= 180;
			e.c = Color.RED;
			entities.add(e);
		}
	}
	public Enemy randomEnemy(){
		String s;
		int i = rand.nextInt(EnemyCache.types.length);
		s = EnemyCache.types[i];
		return EnemyCache.getEntity(s);
	}
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);;
				g2d.setColor(Color.BLUE);
				g2d.setColor(Color.RED);
				for(int i = 0; i < entities.size();i++){
					Entity e = entities.get(i);
					if(e == ship){
						g2d.setColor(Color.GREEN);
					}				
					else{
						g2d.setColor(e.c);
					}

					g2d.fillPolygon(e.getRotatedPolygon());
				}
				for(int i = 0; i < bullets.size();i++){
					Bullet e = bullets.get(i);
					g2d.setColor(e.c);
					g2d.fillPolygon(e.getRotatedPolygon());
				}
				for(int i = 0; i < particleArray.size();i++){
					Particle p = particleArray.get(i);
					g2d.setColor(p.c);
					g2d.fillPolygon(p.getRotatedPolygon());
				}
				g2d.setColor(Color.BLUE);

	}
	public void addParticles(ArrayList<Particle> a){
		this.particleArray.addAll(a);
	}
	public ArrayList<Entity> getTargets(){
		ArrayList<Entity> e = new ArrayList<Entity>();

		e.addAll(entities);
		if(ship != null){
			e.add(ship);
		}
		return e;
	}
	public void update(){
		doKeys();
		repaint();
		int a = entities.size();
		for(int i = 0; i < a;i++){
			Entity e1 = entities.get(i);
			if(!e1.dead){
				e1.update();
				for(int j = i; j < entities.size();j++){
					Entity e2 = entities.get(j);
					if(!e2.dead){
						if(checkCollision(e1, e2)){
							e1.onCollide(e2);
						}
					}
				}
			}

		}
		for(int i = 0; i < bullets.size();i++){
			Bullet b = bullets.get(i);
			for(int j = 0; j < entities.size();j++){
				Entity e = entities.get(j);
				if(!e.dead){
					boolean bo = checkCollision(b, e);
					if(bo && e.team != b.team){
						e.damage(b.calibre);
						bullets.remove(i);
						break;
					}
				}
			}
			b.update();
		}
		for(int i = 0; i < particleArray.size();i++){
			Particle p = particleArray.get(i);
			if(p.dead){
				particleArray.remove(i);
			}
			else{
				p.update();
			}
		}
	}
	public static boolean checkCollision(Entity e1, Entity e2){
		double dist = GameMath.getDistance(e1, e2);
		if(dist > e1.maxRadius + e2.maxRadius){
			return false;
		}
		Polygon poly1 = e1.getRotatedPolygon();
		Polygon poly2 = e2.getRotatedPolygon();
		Point2D[] p1 = new Point2D[poly1.npoints];
		Point2D[] p2 = new Point2D[poly2.npoints];
		for(int i = 0; i < p1.length;i++){
			p1[i] = new Point2D.Double(poly1.xpoints[i],poly1.ypoints[i]);
		}
		for(int i = 0; i < p2.length;i++){
			p2[i] = new Point2D.Double(poly2.xpoints[i],poly2.ypoints[i]);
		}
		for(int i = 0; i < p1.length;i++){
			if(poly2.contains(p1[i])){
				return true;
			}
		}for(int i = 0; i < p2.length;i++){
			if(poly1.contains(p2[i])){
				return true;
			}
		}
		return false;
	}
	public void addBullet(Bullet b){
		if(b != null){
			bullets.add(b);
		}
	}
	public void addBullets(Bullet[] ba){
		for(int i = 0; i < ba.length;i++){
			Bullet b = ba[i];
			addBullet(b);
		}
	}
	public void doKeys(){
		for(int i = 0; i < cooldown.length;i++){
			cooldown[i]--;
		}
		if(ship != null && !ship.dead){
			if(keySet.get(KeyEvent.VK_W)){
				ship.thrust(1);
			}
			if(keySet.get(KeyEvent.VK_A)){
				ship.strafe(-1);
			}	
			if(keySet.get(KeyEvent.VK_S)){
				ship.thrust(-1);
			}	
			if(keySet.get(KeyEvent.VK_D)){
				ship.strafe(1);
			}
			if(keySet.get(KeyEvent.VK_RIGHT)){
				ship.turnRight();
			}
			if(keySet.get(KeyEvent.VK_LEFT)){
				ship.turnLeft();
			}
			if(keySet.get(KeyEvent.VK_F)){
				ship.shoot();
			}
			if(keySet.get(KeyEvent.VK_T)){
				if(cooldown[0] < 0){
					cooldown[0] = 100;
					lockOn = !lockOn;
				}
			}
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {

	}
	@Override
	public void keyPressed(KeyEvent e) {
		keySet.set(e.getKeyCode(),true);

	}
	@Override
	public void keyReleased(KeyEvent e) {
		keySet.set(e.getKeyCode(),false);

	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mousePressed(MouseEvent e) {
		p2 = e.getPoint();	
		p2.y -= 40;
		p2.x -= 10;
		double dist = 99999999;
		for(int i = 0; i < entities.size();i++){
			Ship en = entities.get(i);
			en.playerControl = false;
			if(!en.dead){
				if(GameMath.getDistance(en.getX(), en.getY(), p2.getX(), p2.getY()) < dist){
					dist = GameMath.getDistance(en.getX(), en.getY(), p2.getX(), p2.getY());
					System.out.println(en.getX() + " " + en.getY());
					ship = (Ship) en;
				}
			}
		}
		ship.playerControl = true;

	}
	//	public Ship getShipAtLocation(double x, double y){
	//		return entities.get(1);

	//	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//		spawnEnemy();
	}


}
