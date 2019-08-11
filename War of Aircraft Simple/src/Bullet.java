package game.data.type.unit;

import java.util.ArrayList;
import java.util.HashMap;

import game.data.type.attr.Area;
import game.data.type.attr.Body;
import game.data.type.attr.Status;

public class Bullet extends BasicLivingUnit {
	private static final long serialVersionUID = -808106054160234381L;
	
	public Bullet() {
		super();
		
		modelName[0] = "bulletU";
		modelName[1] = "bulletUR";
		modelName[2] = "bulletR";
		modelName[3] = "bulletDR";
		modelName[4] = "bulletD";
		modelName[5] = "bulletDL";
		modelName[6] = "bulletL";
		modelName[7] = "bulletUL";
	}

	@Override
	public void autoMove(double timePassed, Area border) {
		if(mainBody.getLocationY() < border.getTop() + 10) this.setDelete(true);
		if(mainBody.getLocationY() > border.getBottom() - 10) this.setDelete(true);
		if(mainBody.getLocationX() < border.getLeft() + 10) this.setDelete(true);
		if(mainBody.getLocationX() > border.getRight() - 10) this.setDelete(true);
		
		super.autoMove(timePassed, border);
	}
	
	@Override
	public void collisionHappened(Unit unit) {
		if(unit == null) return;
		if(this.getCamp() != unit.getCamp()) {
			unit.getDamage(this.blood.getCurrent());
		}
	}
	
	@Override
	public void autoAct(double timePassed, boolean[] keyMap, HashMap<String, Unit> unitMap) {
		super.autoAct(timePassed, keyMap, unitMap);
		
		double angle = this.mainBody.getAngleVal();
		final double angl_t = Math.PI / 8.0;
		
		if(angle < angl_t) {
			this.setMovingUp();
		} else if(angle < angl_t * 3) {
			this.setMovingUp();
			this.setMovingRight();
		} else if(angle < angl_t * 5) {
			this.setMovingRight();
		} else if(angle < angl_t * 7) {
			this.setMovingRight();
			this.setMovingDown();
		} else if(angle < angl_t * 9) {
			this.setMovingDown();
		} else if(angle < angl_t * 11) {
			this.setMovingDown();
			this.setMovingLeft();
		} else if(angle < angl_t * 13) {
			this.setMovingLeft();
		} else if(angle < angl_t * 15) {
			this.setMovingLeft();
			this.setMovingUp();
		}
	}
	
	@SuppressWarnings("unchecked")
	public Object getCopy() {
		Bullet ret = new Bullet();
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
