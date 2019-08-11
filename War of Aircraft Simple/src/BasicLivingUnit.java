package game.data.type.unit;

import java.util.ArrayList;
import java.util.HashMap;

import game.data.type.attr.Area;
import game.data.type.attr.Body;
import game.data.type.attr.Status;

public class BasicLivingUnit extends Unit {
	private static final long serialVersionUID = -375054168417515628L;
	
	protected String[] modelName;
	protected int movingStatus;
	protected int keyStatus;
	
	protected String weaponName[];
	protected Status weaponTime[];
	protected Status weaponNumber[];
	
	protected double min_dis;		// 碰撞检测临时变量，用于计算距离自己最近的单位
	protected double damageGet;	// 碰撞检测临时变量，用于计算自己得到的伤害
	
	Unit ex;
	
	
	public BasicLivingUnit() {
		super();
		
		this.modelName = new String[8];
		modelName[0] = "airEnemyU";
		modelName[1] = "airEnemyUR";
		modelName[2] = "airEnemyR";
		modelName[3] = "airEnemyDR";
		modelName[4] = "airEnemyD";
		modelName[5] = "airEnemyDL";
		modelName[6] = "airEnemyL";
		modelName[7] = "airEnemyUL";
		
		this.movingStatus = 0;
		this.keyStatus = 0;
		
		this.weaponName = new String[3];
		this.weaponTime = new Status[3];
		this.weaponNumber = new Status[3];
		for(int i = 0; i != this.weaponTime.length; ++i) {
			this.weaponTime[i] = new Status();
			this.weaponNumber[i] = new Status();
		}
		
		this.setMin_dis(0);
	}
	
	@Override
	public boolean isPlayer() {
		return false;
	}
	
	public void setModelName(int i, String name) {
		if(i < 0 || i > modelName.length) return;
		modelName[i] = name;
	}
	
	public boolean isMovingUp() {
		return (movingStatus & (1 << 3)) > 0;
	}
	public boolean isMovingDown() {
		return (movingStatus & (1 << 2)) > 0;
	}
	public boolean isMovingLeft() {
		return (movingStatus & (1 << 1)) > 0;
	}
	public boolean isMovingRight() {
		return (movingStatus & (1 << 0)) > 0;
	}
	public void setMovingUp() {
		movingStatus = movingStatus | (1 << 3);
		this.stopMovingDown();
	}
	public void setMovingDown() {
		movingStatus = movingStatus | (1 << 2);
		this.stopMovingUp();
	}
	public void setMovingLeft() {
		movingStatus = movingStatus | (1 << 1);
		this.stopMovingRight();
	}
	public void setMovingRight() {
		movingStatus = movingStatus | (1 << 0);
		this.stopMovingLeft();
	}
	public void stopMovingUp() {
		movingStatus = movingStatus & (~(1 << 3));
	}
	public void stopMovingDown() {
		movingStatus = movingStatus & (~(1 << 2));
	}
	public void stopMovingLeft() {
		movingStatus = movingStatus & (~(1 << 1));
	}
	public void stopMovingRight() {
		movingStatus = movingStatus & (~(1 << 0));
	}
	public double getMin_dis() {
		return min_dis;
	}
	public double getDamageGet() {
		return damageGet;
	}
	public void setDamageGet(double damageGet) {
		this.damageGet = damageGet;
	}

	public void setMin_dis(double min_dis) {
		this.min_dis = min_dis;
	}
	
	@Override
	public void initCollitionStatus() {
		this.setMin_dis(Double.MAX_VALUE);
		this.setDamageGet(0.0);
	}
	
	@Override
	public boolean canDetectCollision() {
		return true;
	}

	@Override
	public void adjustSelf(Unit unit, HashMap<String, Unit> unitMap) {
		;
	}

	@Override
	public boolean collisionDetected(Unit unit) {
		if(unit == null) return false;
		if(this.getCamp() == unit.getCamp()) return false;
		return this.mainBody.collisionDetected(unit.mainBody);
	}

	@Override
	public void collisionHappened(Unit unit) {
		if(unit == null) return;
		if(this.getCamp() != unit.getCamp()) {
			unit.getDamage(this.blood.getCurrent());
		}
	}

	@Override
	public boolean isTarget() {
		return true;
	}

	@Override
	public boolean isBullet() {
		return false;
	}

	@Override
	public void autoMove(double timePassed, Area border) {
		// 移动距离		
		double dis = this.speed.getCurrent() * timePassed;
			
		double x = mainBody.getLocationX();
		double y = mainBody.getLocationY();
		if(this.isMovingUp()) {
			if(!border.outRangeTop(y)) mainBody.MoveY(-dis);
		}
		if(this.isMovingDown()) {
			if(!border.outRangeBottom(y)) mainBody.MoveY(dis);
		}
		if(this.isMovingLeft()) {
			if(!border.outRangeLeft(x)) mainBody.MoveX(-dis);
		}
		if(this.isMovingRight()) {
			if(!border.outRangeRight(x)) mainBody.MoveX(dis);
		}
		
		// 调整姿态
		int pos = -1;
		if(this.isMovingUp()) {
			pos = 0;
			if(this.isMovingLeft()) pos = 7;
			else if(this.isMovingRight()) pos = 1;
		} else if(this.isMovingDown()) {
			pos = 4;
			if(this.isMovingRight()) pos = 3;
			else if(this.isMovingLeft()) pos = 5;
		} else if(this.isMovingLeft()) {
			pos = 6;
		} else if(this.isMovingRight()) {
			pos = 2;
		}
		if(pos >= 0) this.mainBody.RotateTo(((double)pos) * Math.PI / 4.0);
	}

	protected void generateWeapon(int i, HashMap<String, Unit> unitMap) {
		if(i < 0 || i > this.weaponName.length) return;
		
		
		if(this.weaponTime[i].isEmpty() && (!this.weaponNumber[i].isEmpty())) {
			this.weaponTime[i].setCurrent(weaponTime[i].getUpperbound());
			
			if(unitMap.containsKey(weaponName[i])) {
				Unit weapon = (Unit) unitMap.get(weaponName[i]).getCopy();
				Body body = weapon.getMainBody();
				body.MoveTo(mainBody.getLocationX(), mainBody.getLocationY());
				body.RotateTo(mainBody.getAngleVal());
				weapon.setMainBody(body);
				weapon.setCamp(this.getCamp());
				unitGenerated.add(weapon);
			}
			
		}
	}
	
	@Override
	public void autoAct(double timePassed, boolean[] keyMap, HashMap<String, Unit> unitMap) {
		for(Status it: this.weaponTime) {
			it.currentDecrease(timePassed);
		}
		if(ex == null) {
			ex = unitMap.get("ex");
			if(ex != null) ex = (Unit) ex.getCopy();
		}
	}

	@Override
	public void getDamage(double damage) {
		damageGet += damage;
	}
	@Override
	public void applyDamage() {
		this.blood.currentDecrease(damageGet);
	}

	@Override
	public void checkSelf() {
		if(blood.isEmpty()) {
			this.setDelete(true);
			if(ex != null) {
				Body bd = ex.getMainBody();
				bd.MoveTo(mainBody.getLocationX(), mainBody.getLocationY());
				ex.setMainBody(bd);
				this.unitGenerated.add(ex);
			}
		}
	}

	@Override
	public boolean canDisplay() {
		return true;
	}

	@Override
	public String getDisplayName() {
		double angle = this.mainBody.getAngleVal();
		final double angl_t = Math.PI / 8.0;
		
		if(angle < angl_t) return this.modelName[0];
		if(angle < angl_t * 3) return this.modelName[1];
		if(angle < angl_t * 5) return this.modelName[2];
		if(angle < angl_t * 7) return this.modelName[3];
		if(angle < angl_t * 9) return this.modelName[4];
		if(angle < angl_t * 11) return this.modelName[5];
		if(angle < angl_t * 13) return this.modelName[6];
		if(angle < angl_t * 15) return this.modelName[7];
		
		return this.modelName[0];
	}

	@Override
	public double getDisplayPosX() {
		return this.mainBody.getLocation().getX();
	}

	@Override
	public double getDisplayPosY() {
		return this.mainBody.getLocation().getY();
	}

	@Override
	public int getDepth() {
		return this.mainBody.getDepth();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getCopy() {
		BasicLivingUnit ret = new BasicLivingUnit();
		ret.frozen = this.frozen;
		ret.mainBody = (Body)this.mainBody.getCopy();
		ret.blood = (Status)this.blood.getCopy();
		ret.speed = (Status)this.speed.getCopy();
		ret.camp = this.camp;
		ret.shouldDeleted = this.shouldDeleted;
		this.unitGenerated = (ArrayList<Unit>) this.unitGenerated.clone();
		
		ret.modelName = this.modelName.clone();
		ret.movingStatus = this.movingStatus;
		ret.keyStatus = this.keyStatus;
		
		ret.weaponName = new String[3];
		for(int i = 0; i != 3; ++i) ret.weaponName[i] = this.weaponName[i];
		
		ret.weaponTime = new Status[3];
		for(int i = 0; i != 3; ++i) ret.weaponTime[i] = (Status) this.weaponTime[i].getCopy();
		
		ret.weaponNumber = new Status[3];
		for(int i = 0; i != 3; ++i) ret.weaponNumber[i] = (Status) this.weaponNumber[i].getCopy();
		
		return ret;
	}
}
