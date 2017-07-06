package pl.projewski.jdungeon.map.direct;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Point2D implements Cloneable {
	public int x;
	public int y;

	public boolean isInsideArea(Area2D area) {
		return Point2D.isInsideArea(this.x, this.y, area.x, area.y, area.width, area.height);
	}

	public boolean isInsideArea(int x, int y, int width, int height) {
		return Point2D.isInsideArea(this.x, this.y, x, y, width, height);
	}

	private static boolean isInsideArea(int px, int py, int ax, int ay, int awidth, int aheight) {
		return px >= ax && px < ax + awidth && py >= ay && py < ay + aheight;
	}

	public boolean isInsideAreaMoveVector(Area2D area, Vector2D vector) {
		return Point2D.isInsideArea(this.x + vector.dx, this.y + vector.dy, area.x, area.y, area.width, area.height);
	}

	public void move(Vector2D vector) {
		this.x += vector.dx;
		this.y += vector.dy;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (obj instanceof Point2D) {
			Point2D p = (Point2D) obj;
			return p.x == x && p.y == y;
		}
		return false;
	}

	@Override
	public Point2D clone() {
		return new Point2D(x, y);
	}

}
