package game.data.type.attr;

import java.io.Serializable;

import game.data.type.Copy;

public class Angle implements Copy, Comparable<Angle>, Serializable {
	private static final long serialVersionUID = -8430390831140348947L;

	public static double PI = 3.141592654;
	
	private BasicAttr angle;
	
	public Angle() {
		this.angle = new BasicAttr(0.0);
	}
	public Angle(double angle) {
		this.angle = new BasicAttr(angle);
	}
	
	public double getAngle() {
		return angle.getNumber();
	}
	public void setAngle(double angle) {
		this.angle.setNumber(angle);
	}
	
	public void rotate(double angle) {
		this.angle.increase(angle);
	}
	public void rotateTo(double angle) {
		this.angle.setNumber(angle);
	}
	
	public int compareTo(Angle angle) {
		if(angle == null) return 1;
		return this.angle.compareTo(angle.angle);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((angle == null) ? 0 : angle.hashCode());
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
		Angle other = (Angle) obj;
		if (angle == null) {
			if (other.angle != null)
				return false;
		} else if (!angle.equals(other.angle))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Angle [angle=" + angle + "]";
	}
	
	@Override
	public Object getCopy() {
		// TODO Auto-generated method stub
		return null;
	}

}
