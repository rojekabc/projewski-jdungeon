package pl.projewski.jdungeon.map.direct;

import org.junit.Assert;
import org.junit.Test;

public class Area2DTest {

	@Test
	public void testGetCommonArea_OneInsideTwo_onEdge() {
		final Area2D one = new Area2D(2, 2, 2, 2);
		final Area2D two = new Area2D(1, 1, 3, 3);
		final Area2D area = one.getCommonArea(two);
		Assert.assertTrue(one.equals(area));
	}

	@Test
	public void testGetCommonArea_OneInsideTwo_inner() {
		final Area2D one = new Area2D(2, 2, 2, 2);
		final Area2D two = new Area2D(1, 1, 4, 4);
		final Area2D area = one.getCommonArea(two);
		Assert.assertTrue(one.equals(area));
	}

	@Test
	public void testGetCommonArea_OneWithTwo_common() {
		final Area2D one = new Area2D(2, 2, 2, 2);
		final Area2D two = new Area2D(3, 1, 1, 4);
		final Area2D area = one.getCommonArea(two);
		Assert.assertTrue(area.equals(new Area2D(3, 2, 1, 2)));
	}

	@Test
	public void testGetCommonArea_OneWithTwo_noCommon() {
		final Area2D one = new Area2D(2, 2, 2, 2);
		final Area2D two = new Area2D(4, 2, 2, 2);
		Assert.assertNull(one.getCommonArea(two));
		Assert.assertNull(two.getCommonArea(one));
	}

}
