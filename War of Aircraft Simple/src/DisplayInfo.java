package game.device;

public class DisplayInfo {
	public String picName;
	public int x, y;
	public int depth;
	public int width;
	public int height;
	
	
	public DisplayInfo() {
		super();
		this.picName = "";
		this.x = 0;
		this.y = 0;
		this.depth = 0;
		this.width = 96;
		this.height = 96;
	}
	public DisplayInfo(String picName, int x, int y) {
		super();
		this.picName = picName;
		this.x = x;
		this.y = y;
		this.depth = 0;
		this.width = 96;
		this.height = 96;
	}
	public DisplayInfo(String picName, int x, int y, int depth) {
		super();
		this.picName = picName;
		this.x = x;
		this.y = y;
		this.width = 96;
		this.height = 96;
		this.depth = depth;
	}
	public DisplayInfo(String picName, int x, int y, int depth, int width, int height) {
		super();
		this.picName = picName;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.depth = depth;
	}
}
