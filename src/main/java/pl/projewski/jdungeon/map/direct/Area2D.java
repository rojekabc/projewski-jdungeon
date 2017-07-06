package pl.projewski.jdungeon.map.direct;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Area2D implements Cloneable {
	public int x;
	public int y;
	public int width;
	public int height;

	public void move(Vector2D v) {
		x += v.dx;
		y += v.dy;
	}

	public boolean isInside(int x, int y, int wdth, int height) {
		return this.x >= x && this.y >= y && this.x + this.width <= width && this.y + this.height <= height;
	}

	public boolean isInside(Area2D area) {
		return isInside(area.x, area.y, area.width, area.height);
	}

	@Override
	public Area2D clone() {
		return new Area2D(x, y, width, height);
	}
}
