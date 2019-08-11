package game.data.type.unit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import game.data.type.Copy;
import game.data.type.attr.Area;
import game.data.type.attr.Body;
import game.data.type.attr.Status;

public abstract class Unit implements Copy, Comparable<Unit>, Serializable {
	private static final long serialVersionUID = -168174367930446886L;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	protected boolean frozen;									// 是否暂停
	protected Body mainBody;									// 身体
	protected Status blood;										// 血量
	protected Status speed;										// 速度
	protected int camp;											// 阵营
	
	protected boolean shouldDeleted;							// 自身是否应当被删除
	
	protected ArrayList<Unit> unitGenerated;						// 产生的目标
	
	public Unit() {
		super();
		this.frozen = false;
		this.mainBody = new Body();
		this.blood = new Status(1.0, 1.0, 0);
		this.speed = new Status(1.0, 1.0, 0);
		this.camp = 0;
		this.shouldDeleted = false;
		this.unitGenerated = new ArrayList<Unit>();
	}
	public Unit(boolean frozen, Body mainBody, Status blood, Status speed, int camp, ArrayList<Unit> unitGenerated) {
		super();
		this.frozen = frozen;
		this.mainBody = (Body)mainBody.getCopy();
		this.blood = (Status)blood.getCopy();
		this.speed = (Status)speed.getCopy();
		this.camp = camp;
		this.shouldDeleted = false;
		this.unitGenerated = unitGenerated;
		this.shouldDeleted = false;
	}
	
	public boolean isPlayer() {
		return false;
	}
	
	public boolean isFrozen() {
		return frozen;
	}
	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}
	public Body getMainBody() {
		return mainBody;
	}
	public void setMainBody(Body mainBody) {
		this.mainBody.setLocation(mainBody.getLocation());
		this.mainBody.setAngle(mainBody.getAngle());
		this.mainBody.setRadius(mainBody.getRadius());
		this.mainBody.setDepth(mainBody.getDepth());
	}
	public Status getBlood() {
		return blood;
	}
	public void setBlood(Status blood) {
		this.blood.setCurrent(blood.getCurrent());
		this.blood.setUpperbound(blood.getUpperbound());
		this.blood.setRecover(blood.getRecover());
	}
	public Status getSpeed() {
		return speed;
	}
	public void setSpeed(Status speed) {
		this.speed.setCurrent(speed.getCurrent());
		this.speed.setUpperbound(speed.getUpperbound());
		this.speed.setRecover(speed.getRecover());
	}
	public int getCamp() {
		return camp;
	}
	public void setCamp(int camp) {
		this.camp = camp;
	}
	public ArrayList<Unit> getUnitGenerated() {
		@SuppressWarnings("unchecked")
		ArrayList<Unit> ret = (ArrayList<Unit>)this.unitGenerated.clone();
		unitGenerated.clear();
		return ret;
	}
	public void setUnitGenerated(ArrayList<Unit> unitGenerated) {
		this.unitGenerated.clear();
		for(Unit it: unitGenerated) {
			this.unitGenerated.add(it);
		}
	}

	// 碰撞检测阶段方法
	public abstract void initCollitionStatus();							// 初始化碰撞状态
	public abstract boolean canDetectCollision();						// 是否可检测碰撞
	public abstract void adjustSelf(Unit unit, HashMap<String, Unit> unitMap);
																		// 根据指定单位调整自身
	public abstract boolean collisionDetected(Unit unit);				// 是否与指定单位发生了碰撞
	public abstract void collisionHappened(Unit unit);					// 处理与指定单位的碰撞
	
	public abstract boolean isTarget();									// 是否是可击中目标，可击中目标之间和可击中目标和子弹之间可碰撞
	public abstract boolean isBullet();									// 是否是子弹，子弹之间不发生碰撞
	
	// 自动计算阶段方法
	public abstract void autoMove(double timePassed, Area border);		// 自动处理移动
	public abstract void autoAct(double timePassed, boolean[] keyMap, HashMap<String, Unit> unitMap);
																		// 自动执行的动作
	
	// 自身状态相关方法
	public abstract void getDamage(double damage);						// 获得伤害
	public abstract void applyDamage();									// 结算伤害
	public abstract void checkSelf();									// 检查自身状态
	public boolean shouldBeDeleted() {									// 是否应当被删除
		return this.shouldDeleted;
	}
	public void setDelete(boolean delete) {								// 设置是否应当被删除
		this.shouldDeleted = delete;
	}
	
	// 显示相关方法
	public abstract boolean canDisplay();								// 是否可以显示
	public abstract String getDisplayName();							// 获得显示内容的标识
	public abstract double getDisplayPosX();							// 获得显示时对应的游戏坐标
	public abstract double getDisplayPosY();							// 获得显示时对应的游戏坐标
	public abstract int getDepth();										// 获得显示时对应的深度
	
	@Override
	public abstract Object getCopy();

	@Override
	public int compareTo(Unit unit) {
		return this.mainBody.compareTo(unit.mainBody);
	}
}
