package game.data.type.attr;

import java.io.Serializable;

import game.data.type.Copy;

public class BasicAttr implements Comparable<BasicAttr>, Serializable, Copy {
	private static final long serialVersionUID = -7622934447041589381L;
	
	private static final double limit = 0.001;
	static boolean less(double a, double b) {
		return b - a > limit;
	}
	static boolean equals(double a, double b) {
		return Math.abs(a - b) < limit;
	}
	
	
	private double number;

	public BasicAttr() {
		super();
		this.number = 0;
	}
	
	public BasicAttr(double number) {
		super();
		this.number = number;
	}

	public double getNumber() {
		return number;
	}

	public void setNumber(double number) {
		this.number = number;
	}
	
	
	public void increase(double number) {
		this.number += number;
	}
	public void decrease(double number) {
		this.number -= number;
	}

	public int compareTo(BasicAttr attr) {
		if(attr == null) return 1;
		
		if(less(this.number, attr.number)) return -1;
		else if(equals(this.number, attr.number)) return 0;
		else return 1;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(number);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		BasicAttr other = (BasicAttr) obj;
		if (equals(this.number, other.number)) return true;
		else return false;
	}
	
	@Override
	public String toString() {
		return "BasicAttr [number=" + number + "]";
	}
	
	@Override
	public Object getCopy() {
		return new BasicAttr(this.number);
	}
}
