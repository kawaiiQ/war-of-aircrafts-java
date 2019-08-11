package game.data.type.unit;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import game.data.type.attr.Body;
import game.data.type.attr.Status;

public class ControllableAircraft extends BasicLivingUnit {
	private static final long serialVersionUID = 1609624804037811134L;
	
	public ControllableAircraft() {
		super();
		modelName[0] = "airMeU";
		modelName[1] = "airMeUR";
		modelName[2] = "airMeR";
		modelName[3] = "airMeDR";
		modelName[4] = "airMeD";
		modelName[5] = "airMeDL";
		modelName[6] = "airMeL";
		modelName[7] = "airMeUL";
		
		weaponName[0] = "bullet";
		weaponTime[0].setCurrent(0);
		weaponTime[0].setUpperbound(100);
		weaponNumber[0].setCurrent(1);
		weaponNumber[0].setUpperbound(1);
	}

	@Override
	public boolean isPlayer() {
		return true;
	}
	
	@Override
	public void autoAct(double timePassed, boolean[] keyMap, HashMap<String, Unit> unitMap) {
		super.autoAct(timePassed, keyMap, unitMap);
		
		if(keyMap[KeyEvent.VK_W]) {
			if((this.keyStatus & (1 << 3)) == 0) {					// 此刻之前向上未处于被按下状态
				this.keyStatus = this.keyStatus | (1 << 3);
				this.setMovingUp();
			}
		} else {
			if((this.keyStatus & (1 << 3)) > 0) {
				this.keyStatus = this.keyStatus & (~(1 << 3));
				this.stopMovingUp();
				if((this.keyStatus & (1 << 2)) > 0) {					// 如果当前向下处于被按下状态
					this.setMovingDown();
				}
			}
		}
		
		if(keyMap[KeyEvent.VK_S]) {
			if((this.keyStatus & (1 << 2)) == 0) {					// 此刻之前向下未处于被按下状态
				this.keyStatus = this.keyStatus | (1 << 2);
				this.setMovingDown();
			}
		} else {
			if((this.keyStatus & (1 << 2)) > 0) {
				this.keyStatus = this.keyStatus & (~(1 << 2));
				this.stopMovingDown();
				if((this.keyStatus & (1 << 3)) > 0) {				// 如果当前向上处于被按下状态
					this.setMovingUp();
				}
			}
		}
			
		if(keyMap[KeyEvent.VK_A]) {
			if((this.keyStatus & (1 << 1)) == 0) {					// 此刻之前向左未处于被按下状态
				this.keyStatus = this.keyStatus | (1 << 1);
				this.setMovingLeft();
			}
		} else {
			if((this.keyStatus & (1 << 1)) > 0) {
				this.keyStatus = this.keyStatus & (~(1 << 1));
				this.stopMovingLeft();
				if((this.keyStatus & (1 << 0)) > 0) {					// 如果当前向右处于被按下状态
					this.setMovingRight();
				}
			}
		}
		
		if(keyMap[KeyEvent.VK_D]) {
			if((this.keyStatus & (1 << 0)) == 0) {						// 此刻之前向右未处于被按下状态
				this.keyStatus = this.keyStatus | (1 << 0);
				this.setMovingRight();
			}
		} else {
			if((this.keyStatus & (1 << 0)) > 0) {
				this.keyStatus = this.keyStatus & (~(1 << 0));
				this.stopMovingRight();
				if((this.keyStatus & (1 << 1)) > 0) {					// 如果当前向左处于被按下状态
					this.setMovingLeft();
				}
			}
		}
		
		if(keyMap[KeyEvent.VK_J]) {
			this.generateWeapon(0, unitMap);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object getCopy() {
		ControllableAircraft ret = new ControllableAircraft();
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

