package game.data.type.unit;

import java.util.ArrayList;
import java.util.HashMap;

import game.data.type.attr.Body;
import game.data.type.attr.Status;

public class FollowingAircraft extends SmartAircraft {
	private static final long serialVersionUID = 3053638378617796055L;
	
	public FollowingAircraft() {
		super();
	}

	@Override
	public void autoAct(double timePassed, boolean[] keyMap, HashMap<String, Unit> unitMap) {
		super.autoAct(timePassed, keyMap, unitMap);
		
		if(target != null && !target.shouldBeDeleted()) {
			Body tBody = target.getMainBody();
			Body iBody = this.mainBody;
			
			if(tBody.getLocationX() + 20 < iBody.getLocationX()) this.setMovingLeft();
			else if(tBody.getLocationX() - 20 > iBody.getLocationX()) this.setMovingRight();
			else {
				this.stopMovingLeft();
				this.stopMovingRight();
			}
			
			if(tBody.getLocationY() + 20 < iBody.getLocationY()) this.setMovingUp();
			else if(tBody.getLocationY() - 20 > iBody.getLocationY()) this.setMovingDown();
			else {
				this.stopMovingUp();
				this.stopMovingDown();
			}
			
			this.generateWeapon(0, unitMap);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object getCopy() {
		FollowingAircraft ret = new FollowingAircraft();
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
	
	@Override
	public void checkSelf() {
		super.checkSelf();
		if(target != null && target.shouldBeDeleted()) target = null;
	}
}
