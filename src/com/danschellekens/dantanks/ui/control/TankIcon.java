package com.danschellekens.dantanks.ui.control;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dangame.rect_ui.UIElement;
import com.danschellekens.dantanks.data.Player;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class TankIcon extends UIElement {
	Image baseImage;
	Image topImage;

	Player player;

	public TankIcon(UIElement parent, float x, float y) {
		super(parent, new Rectangle(x, y, 50, 50));

		try {
			baseImage = new Image("level/tank/base.png");
			topImage = new Image("level/tank/color.png");
		} 
		catch (SlickException e) {
			throw new OopsieException(e);
		}
	}

	@Override
	public void update(DanInput input) {

	}

	@Override
	protected void renderInPosition(Artist artist) {
		artist.push();
		artist.shape().draw(new Circle(25, 25, 25), new Color(0f, 0f, 0f, 0.1f));

		if (player != null) {
			artist.translate(8, 11);
			artist.image().draw(baseImage, 0, 0);
			artist.image().draw(topImage, 0, 0, player.getColor().getColor());

			artist.push();
			artist.translate(15, 7);
			artist.rotate(65);
			artist.line().draw(0, 0, 0, -15, new Color(85, 85, 85), 4);
			artist.shape().draw(new Circle(0, 0, 3), new Color(85, 85, 85));
			artist.pop();
		}
		
		artist.pop();
	}

	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
}
