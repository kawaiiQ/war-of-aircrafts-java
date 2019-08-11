package game.data.type.unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import game.data.type.attr.Body;
import game.data.type.attr.Status;

public class RandomMoveAircraft extends SmartAircraft {
	private static final long serialVersionUID = 7014613117588117860L;
	
	private Status randomTime;
	private Random random;
	
	public RandomMoveAircraft() {
		super();
		this.randomTime = new Status(0.0, 800.0);
		this.random = new Random();
	}
	
	@Override
	public void autoAct(double timePassed, boolean[] keyMap, HashMap<String, Unit> unitMap) {
		super.autoAct(timePassed, keyMap, unitMap);
		
		this.generateWeapon(0, unitMap);
		
		randomTime.currentDecrease(timePassed);
		if(randomTime.isEmpty()) {
			randomTime.setCurrent(randomTime.getUpperbound());
			
			int dirX = random.nextInt(3);
			int dirY = random.nextInt(3);
			
			if(dirX == 0) {
				this.setMovingUp();
			} if(dirX == 1) {
				this.setMovingDown();
			} else if(this.isMovingLeft() || this.isMovingRight()) {
				this.stopMovingUp();
				this.stopMovingDown();
			}
			
			if(dirY == 0) {
				this.setMovingLeft();
			} if(dirY == 1) {
				this.setMovingRight();
			} else if(this.isMovingUp() || this.isMovingDown()) {
				this.stopMovingLeft();
				this.stopMovingRight();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object getCopy() {
		RandomMoveAircraft ret = new RandomMoveAircraft();
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
