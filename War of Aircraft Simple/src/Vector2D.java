package game.data.type.attr;

import java.io.Serializable;

import game.data.type.Copy;

public class Vector2D implements Comparable<Vector2D>, Serializable, Copy {
	private static final long serialVersionUID = -8995728096772578888L;
	
	private BasicAttr x;
	private BasicAttr y;
	
	public Vector2D() {
		super();
		this.x = new BasicAttr(0.0);
		this.y = new BasicAttr(0.0);
	}
	public Vector2D(double x, double y) {
		super();
		this.x = new BasicAttr(x);
		this.y = new BasicAttr(y);
	}
	
	public double getX() {
		return x.getNumber();
	}
	public void setX(double x) {
		this.x.setNumber(x);
	}
	public double getY() {
		return y.getNumber();
	}
	public void setY(double y) {
		this.y.setNumber(y);
	}
	
	public void moveX(double distance) {
		x.increase(distance);
	}
	public void moveY(double distance) {
		y.increase(distance);
	}
	public void move(double dis_x, double dis_y) {
		this.moveX(dis_x);
		this.moveY(dis_y);
	}
	
	public void moveToX(double pos_x) {
		x.setNumber(pos_x);
	}
	public void moveToY(double pos_y) {
		y.setNumber(pos_y);
	}
	public void moveTo(double pos_x, double pos_y) {
		this.moveToX(pos_x);
		this.moveToY(pos_y);
	}
	
	public double distanceTo(double x, double y) {
		double dis_x = this.x.getNumber() - x;
		double dis_y = this.y.getNumber() - y;
		return Math.sqrt(dis_x * dis_x + dis_y * dis_y);
	}
	public double distanceTo(Vector2D v) {
		return this.distanceTo(v.x.getNumber(), v.y.getNumber());
	}
	
	public int compareTo(Vector2D v) {
		if(v == null) return 1;
		
		if(x.compareTo(v.x) != 0) return x.compareTo(v.x);
		else return y.compareTo(v.y);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
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
		Vector2D other = (Vector2D) obj;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Vector2D [x=" + x + ", y=" + y + "]";
	}

	@Override
	public Object getCopy() {
		return new Vector2D(x.getNumber(), y.getNumber());
	}
}
