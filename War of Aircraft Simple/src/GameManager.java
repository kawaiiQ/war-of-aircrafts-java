package game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;



import game.device.Display;
import game.device.DisplayInfo;
import game.device.Keyboard;
import game.device.Window;
import game.data.type.attr.Area;
import game.data.type.attr.Body;
import game.data.type.attr.Status;
import game.data.type.unit.Bullet;
import game.data.type.unit.ControllableAircraft;
import game.data.type.unit.FollowingAircraft;
import game.data.type.unit.RandomMoveAircraft;
import game.data.type.unit.Unit;

public class GameManager {
	private Semaphore semp_display;
	private Semaphore semp_keyboard;
	
	private boolean[] keyMap;
	
	private ConcurrentHashMap<String, BufferedImage> picMap;
	private CopyOnWriteArrayList<DisplayInfo> displayList;
	
	private long lastTime;
	private double timePassed;
	
	private Area screenArea;
	private Area gameArea;
	
	private HashMap<String, Unit> unitMap;
	
	private LinkedList<Unit> unitList;
	private ArrayList<Unit> collisionList;
	private ArrayList<Unit> generateList;
	
	private Status generateTime1;
	private Status generateTime2;
	
	boolean dead = false;
	
	Random random;
	
	private long getCurrentTime() {
		Date date = new Date();
		return date.getTime();
	}
	
	public GameManager() {
		semp_display = new Semaphore(1);
		semp_keyboard = new Semaphore(1);
		
		unitList = new LinkedList<Unit>();
		collisionList = new ArrayList<Unit>();
		generateList = new ArrayList<Unit>();
		
		timePassed = 0;
		lastTime = this.getCurrentTime();
		
		screenArea = new Area();
		screenArea.setWidth(1024);
		screenArea.setHeight(600);
		screenArea.moveAsCenter(0, 0);
		
		gameArea = new Area();
		gameArea.setWidth(1024);
		gameArea.setHeight(600);
		gameArea.moveAsCenter(0, 0);
		
		this.generateTime1 = new Status(1000, 2000, 0);
		this.generateTime2 = new Status(2000, 6000, 0);
		
		this.unitMap = new HashMap<String, Unit>();
		this.keyMap = new boolean[512];
		this.picMap = new ConcurrentHashMap<String, BufferedImage>();
		this.displayList = new CopyOnWriteArrayList<DisplayInfo>();
		
		Display display = new Display(this.semp_display, this.picMap, this.displayList);
		Keyboard keyboard = new Keyboard(semp_keyboard, this.keyMap);
		
		random = new Random();

		new Window(display, keyboard);
		
		// ----- Init ---------------
		Bullet bullet = new Bullet();
		bullet.setSpeed(new Status(1.2, 1.0));
		unitMap.put("bullet", bullet);
		
		Bullet eBullet = new Bullet();
		eBullet.setModelName(0, "EbulletU");
		eBullet.setModelName(1, "EbulletUR");
		eBullet.setModelName(2, "EbulletR");
		eBullet.setModelName(3, "EbulletDR");
		eBullet.setModelName(4, "EbulletD");
		eBullet.setModelName(5, "EbulletDL");
		eBullet.setModelName(6, "EbulletL");
		eBullet.setModelName(7, "EbulletUL");
		eBullet.setSpeed(new Status(0.3, 1.0));
		unitMap.put("eBullet", eBullet);
		
		ControllableAircraft air = new ControllableAircraft();
		air.setSpeed(new Status(0.3, 1.0));
		Body bdm = air.getMainBody();
		bdm.setRadius(25);
		air.setBlood(new Status(1.0, 1.0, 0.0));
		air.setMainBody(bdm);
		air.setSpeed(new Status(0.3, 1.0));
		air.setCamp(1);
		
		FollowingAircraft air2 = new FollowingAircraft();
		air2.setBlood(new Status(1.0, 1.0, 0.0));
		Body bd = air2.getMainBody();
		bd.setRadius(25);
		bd.Move(100, 100);
		air2.setMainBody(bd);
		air2.setSpeed(new Status(0.15, 1.0));
		air2.setCamp(2);
		
		RandomMoveAircraft air3 = new RandomMoveAircraft();
		air3.setBlood(new Status(1.0, 1.0, 0.0));
		Body bd1 = air3.getMainBody();
		bd1.setRadius(25);
		bd1.Move(-100, -100);
		air3.setMainBody(bd1);
		air3.setSpeed(new Status(0.1, 1.0));
		air3.setCamp(2);
		
		unitMap.put("Player", air);
		unitMap.put("Enemy2", air2);
		unitMap.put("Enemy1", air3);
		
		dead = false;
	}
	
	public void GameStart() throws InterruptedException {
		unitList.add((Unit)unitMap.get("Player").getCopy());
		dead = false;
		// --------------------------
		
		while(true) {
			long currentTime = (this.getCurrentTime());
			this.timePassed = ((double)(currentTime - this.lastTime));
			
			if(timePassed < 17) {
				Thread.sleep(16 - (long)timePassed);
				continue;
			}
			
			this.lastTime = currentTime;			
			
			this.OneStep();
			
			if(!dead) {
				generateTime1.currentDecrease(timePassed);
				if(generateTime1.isEmpty()) {
					generateTime1.setCurrent(generateTime1.getUpperbound());
					
					RandomMoveAircraft ra = (RandomMoveAircraft) unitMap.get("Enemy1").getCopy();
					Body abd = ra.getMainBody();
					int b = random.nextInt(4);
					double x = 0, y = 0;
					if(b == 0) {
						x = gameArea.getLeft();
						y = random.nextInt((int)gameArea.getBottom());
					} else if(b == 1) {
						x = gameArea.getRight();
						y = random.nextInt((int)gameArea.getBottom());
					} else if(b == 2) {
						y = gameArea.getTop();
						x = random.nextInt((int)gameArea.getRight());
					} else {
						y = gameArea.getBottom();
						x = random.nextInt((int)gameArea.getRight());
					}
					abd.MoveTo(x, y);
					ra.setMainBody(abd);
					unitList.add(ra);
				}
				
				generateTime2.currentDecrease(timePassed);
				if(generateTime2.isEmpty()) {
					generateTime2.setCurrent(generateTime1.getUpperbound());
					
					FollowingAircraft ra = (FollowingAircraft) unitMap.get("Enemy2").getCopy();
					Body abd = ra.getMainBody();
					int b = random.nextInt(4);
					double x = 0, y = 0;
					if(b == 0) {
						x = gameArea.getLeft();
						y = random.nextInt((int)gameArea.getBottom());
					} else if(b == 1) {
						x = gameArea.getRight();
						y = random.nextInt((int)gameArea.getBottom());
					} else if(b == 2) {
						y = gameArea.getTop();
						x = random.nextInt((int)gameArea.getRight());
					} else {
						y = gameArea.getBottom();
						x = random.nextInt((int)gameArea.getRight());
					}
					abd.MoveTo(x, y);
					ra.setMainBody(abd);
					unitList.add(ra);
				}
			}
			
			try {
				semp_display.acquire();				
				this.displayList.clear();
				if(dead) {
					this.displayList.add(new DisplayInfo("Dead", 250, 180, 0, 549, 98));
				}
				for(Unit iter: this.unitList) {
					if(iter.canDisplay()) {
						String name = iter.getDisplayName();
						int depth = iter.getDepth();
						int x = (int) this.coordinateTransformX(iter.getDisplayPosX());
						int y = (int) this.coordinateTransformY(iter.getDisplayPosY());
						this.displayList.add(new DisplayInfo(name, x, y, depth));
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				semp_display.release();
			}
		}
	}
	
	private void OneStep() {
	// 处理角色自动行动
		for(Unit it: this.unitList) {
			it.autoAct(timePassed, this.keyMap, this.unitMap);
		}
	// 处理角色移动
		for(Unit it: this.unitList) {
			it.autoMove(timePassed, gameArea);
		}
	// 处理碰撞检测
		this.collisionList.clear();
		for(Unit it: this.unitList) {
			if(it.canDetectCollision()) this.collisionList.add(it);
		}
		
		// 初始化碰撞检测状态
		for(Unit i: this.collisionList) {
			i.initCollitionStatus();
		}
		// 两两间姿态调整
		for(Unit i: this.collisionList) for(Unit j: this.collisionList) if(i != j) {
			i.adjustSelf(j, unitMap);
		}
		// 两两间碰撞检测
		for(Unit i: this.collisionList) for(Unit j: this.collisionList) if(i != j) {
			if(i.collisionDetected(j)) i.collisionHappened(j);
		}
		// 结算伤害
		for(Unit i: this.collisionList) {
			i.applyDamage();
		}
	// 处理生成单位
		for(Unit it: this.unitList) {
			ArrayList<Unit> generated = it.getUnitGenerated();
			for(Unit newUnit: generated) {
				this.generateList.add(newUnit);
			}
		}
		for(Unit it: this.generateList) {
			this.unitList.add(it);
		}
		this.generateList.clear();
	// 单位状态检测
		for(Unit it: this.unitList) {
			it.checkSelf();
		}
	// 删除无用单位
		Iterator<Unit> it = unitList.iterator();
		while(it.hasNext()) {
			Unit cur = it.next();
			if(cur.shouldBeDeleted()) {
				if(cur.isPlayer()) this.dead = true;
				it.remove();
			}
		}
	}
	
	private double coordinateTransformX(double x) {
		return x - screenArea.getLeft() - 48.0;
		
	}
	private double coordinateTransformY(double y) {
		return y - screenArea.getTop() - 48.0;
		
	}
}
