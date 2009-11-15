package game;


public class ColorPoint {
	
	private int x = -1;
	private int y = -1;
	private int color = -1;
	
	public ColorPoint(int X, int Y){
		setX(X);
		setY(Y);
	}
	
	public int getX() {
		return x;
	}
	private void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	private void setY(int y) {
		this.y = y;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	
	public boolean equals(Object o){
		boolean equals = false;
		if(o instanceof ColorPoint){
			ColorPoint p = (ColorPoint)o;
			if(p.getX() == getX() && p.getY() == getY()){
				equals = true;
			}
		}
		return equals;
	}

}
