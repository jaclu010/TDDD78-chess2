package se.liu.ida.jaclu010carfo452.tddd78.chess2;

/**
 * A Point2D, but for integers
 * @author jaclu010, carfo452
 */
public class Point
{
    private int y, x;

    public Point(final int y, final int x) {
	this.y = y;
	this.x = x;
    }

    public int getY() {
	return y;
    }

    public int getX() {
	return x;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public void setX(final int x) {
        this.x = x;
    }
}
