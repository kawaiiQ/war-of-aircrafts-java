package game.data.type.attr;

import java.io.Serializable;

import game.data.type.Copy;

public class Status implements Comparable<Status>, Serializable, Copy {
	private static final long serialVersionUID = 1483419815686463127L;
	
	private BasicAttr current;
	private BasicAttr upperbound;
	private BasicAttr recover;
	
	
	public Status() {
		super();
		this.current = new BasicAttr(0);
		this.upperbound = new BasicAttr(0);
		this.recover = new BasicAttr(0);
	}
	public Status(double current) {
		super();
		this.current = new BasicAttr(current);
		this.upperbound = new BasicAttr(0);
		this.recover = new BasicAttr(0);
	}
	public Status(double current, double upperbound) {
		super();
		this.current = new BasicAttr(current);
		this.upperbound = new BasicAttr(upperbound);
		this.recover = new BasicAttr(0);
	}
	public Status(double current, double upperbound, double recover) {
		super();
		this.current = new BasicAttr(current);
		this.upperbound = new BasicAttr(upperbound);
		this.recover = new BasicAttr(recover);
	}
	
	public double getCurrent() {
		return current.getNumber();
	}
	public void setCurrent(double current) {
		this.current.setNumber(current);
	}
	public double getUpperbound() {
		return upperbound.getNumber();
	}
	public void setUpperbound(double upperbound) {
		this.upperbound.setNumber(upperbound);
	}
	public double getRecover() {
		return recover.getNumber();
	}
	public void setRecover(double recover) {
		this.recover.setNumber(recover);
	}
	
	private void currentLimit() {
		if(BasicAttr.less(upperbound.getNumber(), current.getNumber())) {
			current.setNumber(upperbound.getNumber());
		}
		if(BasicAttr.less(current.getNumber(), 0.0)) {
			current.setNumber(0.0);
		}
	}
	public void currentIncrease(double number) {
		current.increase(number);
		currentLimit();
	}
	public void currentDecrease(double number) {
		current.decrease(number);
		currentLimit();
	}
	public void currentRecover(double timePassed) {
		current.increase(timePassed * recover.getNumber());
		currentLimit();
	}
	
	public boolean isEmpty() {
		return BasicAttr.equals(current.getNumber(), 0.0);
	}
	
	public int compareTo(Status status) {
		if(status == null) return 1;
		
		if(current.compareTo(status.current) != 0) return current.compareTo(status.current);
		else if(upperbound.compareTo(status.upperbound) != 0) return upperbound.compareTo(status.upperbound);
		else return recover.compareTo(status.recover);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((current == null) ? 0 : current.hashCode());
		result = prime * result + ((recover == null) ? 0 : recover.hashCode());
		result = prime * result + ((upperbound == null) ? 0 : upperbound.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Status other = (Status) obj;
		if (current == null) {
			if (other.current != null)
				return false;
		} else if (!current.equals(other.current))
			return false;
		if (recover == null) {
			if (other.recover != null)
				return false;
		} else if (!recover.equals(other.recover))
			return false;
		if (upperbound == null) {
			if (other.upperbound != null)
				return false;
		} else if (!upperbound.equals(other.upperbound))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Status [current=" + current + ", upperbound=" + upperbound + ", recover=" + recover + "]";
	}
	
	@Override
	public Object getCopy() {
		return new Status(current.getNumber(), upperbound.getNumber(), recover.getNumber());
	}
}
