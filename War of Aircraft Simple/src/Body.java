package game.data.type.attr;

import java.io.Serializable;

import game.data.type.Copy;

public class Body implements Comparable<Body>, Copy, Serializable {
	private static final long serialVersionUID = -6029750996602787756L;
	
	public static final int GROUND = -10;
	public static final int LOW = -5;
	public static final int ZERO = 0;
	public static final int HIGH = 5;
	public static final int SPACE = 10;
	
	private Vector2D location;		// 位置
	private Angle angle;			// 角度
	private BasicAttr radius;		// 半径
	private int depth;				// 深度（决定贴图顺序）
	
	public Body() {
		super();
		this.location = new Vector2D(0.0, 0.0);
		this.angle = new Angle(0.0);
		this.radius = new BasicAttr(0.0);
		this.depth = 0;
	}
	public Body(Vector2D location, Angle angle, double radius) {
		super();
		this.location = new Vector2D(location.getX(), location.getY());
		this.angle = new Angle(angle.getAngle());
		this.radius = new BasicAttr(radius);
		this.depth = 0;
	}
	public Body(double x, double y, double angle, double radius) {
		super();
		this.location = new Vector2D(x, y);
		this.angle = new Angle(angle);
		this.radius = new BasicAttr(radius);
		this.depth = 0;
	}
	public Body(Vector2D location, Angle angle, double radius, int depth) {
		super();
		this.location = new Vector2D(location.getX(), location.getY());
		this.angle = new Angle(angle.getAngle());
		this.radius = new BasicAttr(radius);
		this.depth = depth;
	}
	public Body(double x, double y, double angle, double radius, int depth) {
		super();
		this.location = new Vector2D(x, y);
		this.angle = new Angle(angle);
		this.radius = new BasicAttr(radius);
		this.depth = depth;
	}

	public Vector2D getLocation() {
		return location;
	}
	public double getLocationX() {
		return location.getX();
	}
	public double getLocationY() {
		return location.getY();
	}
	public void setLocation(Vector2D location) {
		this.location = location;
	}
	public Angle getAngle() {
		return angle;
	}
	public double getAngleVal() {
		return angle.getAngle();
	}
	public void setAngle(Angle angle) {
		this.angle = angle;
	}
	public double getRadius() {
		return radius.getNumber();
	}
	public void setRadius(double radius) {
		this.radius.setNumber(radius);
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	public void MoveX(double dis_x) {
		location.moveX(dis_x);
	}
	public void MoveY(double dis_y) {
		location.moveY(dis_y);
	}
	public void Move(double dis_x, double dis_y) {
		location.move(dis_x, dis_y);
	}
	public void MoveToX(double pos_x) {
		location.moveToX(pos_x);
	}
	public void MoveToY(double pos_y) {
		location.moveToY(pos_y);
	}
	public void MoveTo(double pos_x, double pos_y) {
		location.moveTo(pos_x, pos_y);
	}
	
	public void Rotate(double angle) {
		this.angle.rotate(angle);
	}
	public void RotateTo(double angle) {
		this.angle.rotateTo(angle);
	}
	
	public double distanceTo(double x, double y) {
		return this.distanceTo(x, y);
	}
	public double distanceTo(Vector2D v) {
		return this.location.distanceTo(v);
	}
	public double distanceTo(Body body) {
		return this.location.distanceTo(body.location);
	}
	public boolean collisionDetected(Body body) {
		double dis = this.location.distanceTo(body.location);
		return dis < this.radius.getNumber() + body.radius.getNumber();
	}
	
	public int compareTo(Body body) {
		if(body == null) return 1;
		
		if(depth != body.depth) return Integer.compare(depth, body.depth);
		else if(!location.equals(body.location)) return location.compareTo(body.location);
		else if(!angle.equals(body.angle)) return angle.compareTo(body.angle);
		else return radius.compareTo(body.radius);
	}
	
	@Override
	public String toString() {
		return "Body [location=" + location + ", angle=" + angle + ", radius=" + radius + ", depth=" + depth + "]";
	}
	@Override
	public Object getCopy() {
		return new Body(this.location, this.angle, this.radius.getNumber(), this.depth);
	}

}
