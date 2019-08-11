package game.data.type.attr;

import java.io.Serializable;

public class Area implements Serializable {
	private static final long serialVersionUID = -2517637224759791158L;
	
	private double top, bottom, left, right;

	public Area() {
		super();
		this.top = 0.0;
		this.bottom = 0.0;
		this.left = 0.0;
		this.right = 0.0;
	}
	public Area(double top, double bottom, double left, double right) {
		super();
		this.top = top;
		this.bottom = bottom;
		this.left = left;
		this.right = right;
	}
	
	public double getTop() {
		return top;
	}
	public void setTop(double top) {
		this.top = top;
	}
	public double getBottom() {
		return bottom;
	}
	public void setBottom(double bottom) {
		this.bottom = bottom;
	}
	public double getLeft() {
		return left;
	}
	public void setLeft(double left) {
		this.left = left;
	}
	public double getRight() {
		return right;
	}
	public void setRight(double right) {
		this.right = right;
	}
	
	public void setWidth(double width) {
		right = left + width;
	}
	public void setHeight(double height) {
		bottom = top + height;
	}
	
	public boolean outRange(double x, double y) {
		if(BasicAttr.less(x, left) || BasicAttr.less(right, x)) return true;
		if(BasicAttr.less(y, top) || BasicAttr.less(bottom, y)) return true;
		return false;
	}
	public boolean outRangeTop(double y) {
		return BasicAttr.less(y, top);
	}
	public boolean outRangeBottom(double y) {
		return BasicAttr.less(bottom, y);
	}
	public boolean outRangeLeft(double x) {
		return BasicAttr.less(x, left);
	}
	public boolean outRangeRight(double x) {
		return BasicAttr.less(right, x);
	}
	
	public void move(double x, double y) { // 移动指定距离
		top += y; bottom += y;
		left += x; right += x;
	}
	
	public void moveAsCenter(double x, double y) { // 移动至以指定位置为中心点
		double width = (right - left) / 2.0;
		double height = (bottom - top) / 2.0;
		
		top = y - height; bottom = y + height;
		left = x - width; right = x + width;
	}
}
