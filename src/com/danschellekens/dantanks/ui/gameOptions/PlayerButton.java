package com.danschellekens.dantanks.ui.gameOptions;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dangame.rect_ui.ImageViewer;
import com.danschellekens.dangame.rect_ui.UIElement;
import com.danschellekens.dantanks.data.PlayerSettings;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.ui.control.ColorBarButton;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class PlayerButton extends ColorBarButton {
	PlayerSettings player;
	ImageViewer tankIcon;
	ImageViewer aiIcon;
	
	public PlayerButton(UIElement parent, int x, int y, int w, PlayerSettings playerSettings) {
		super(parent, x, y, w, 50);
		
		this.player = playerSettings;
		
		try {
			tankIcon = new ImageViewer(this, new Rectangle(14, 4, 32, 32), new Image("ui/tankIcon.png"));
			aiIcon = new ImageViewer(this, new Rectangle(14, 4, 32, 32), new Image("ui/aiIcon.png"));
		}
		catch (SlickException e) {
			throw new OopsieException(e);
		}
		
		fillIn();
	}
	
	@Override
	public void update(DanInput input) {
		fillIn();
		
		super.update(input);
		tankIcon.update(input);
		aiIcon.update(input);
	}
	void fillIn() {
		setText(player.getName());
		setColor(player.getColor().getColor());
		tankIcon.setFilter(player.getColor().getColor());
		aiIcon.setFilter(player.getColor().getColor());
	}

	@Override
	protected void renderInPosition(Artist artist) {
		super.renderInPosition(artist);
		
		if (!player.isAI()) {
			tankIcon.render(artist);
		}
		else {
			aiIcon.render(artist);
		}
	}

	public PlayerSettings getPlayer() {
		return player;
	}
}
