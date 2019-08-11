package game.data.type.unit;

import java.util.ArrayList;
import java.util.HashMap;

import game.data.type.attr.Area;
import game.data.type.attr.Body;
import game.data.type.attr.Status;

public class SmartAircraft extends BasicLivingUnit {
	private static final long serialVersionUID = -1259305844565057973L;
	Unit target;
	
	public SmartAircraft() {
		this.weaponName[0] = "eBullet";
		this.weaponTime[0].setCurrent(1000);
		this.weaponTime[0].setUpperbound(2000);
		this.weaponNumber[0].setCurrent(1);
		this.weaponNumber[0].setUpperbound(1);
	}
	
	@Override
	public void autoMove(double timePassed, Area border) {
		if(mainBody.getLocationY() < border.getTop() + 10) this.setMovingDown();
		if(mainBody.getLocationY() > border.getBottom() - 10) this.setMovingUp();
		if(mainBody.getLocationX() < border.getLeft() + 10) this.setMovingRight();
		if(mainBody.getLocationX() > border.getRight() - 10) this.setMovingLeft();
		
		super.autoMove(timePassed, border);
	}
	
	@Override
	public void initCollitionStatus() {
		super.initCollitionStatus();
		target = null;
	}
	
	@Override
	public void adjustSelf(Unit unit, HashMap<String, Unit> unitMap) {
		if(this.getCamp() != unit.getCamp()) {
			double distance = this.mainBody.distanceTo(unit.getMainBody());
			
			if(distance < 300 && distance < this.min_dis) {
				this.setMin_dis(distance);
				target = unit;
			}
		}
	}
	
	@Override
	public void autoAct(double timePassed, boolean[] keyMap, HashMap<String, Unit> unitMap) {
		super.autoAct(timePassed, keyMap, unitMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object getCopy() {
		SmartAircraft ret = new SmartAircraft();
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
