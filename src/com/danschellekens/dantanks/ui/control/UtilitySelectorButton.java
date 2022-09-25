package com.danschellekens.dantanks.ui.control;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;

import com.danschellekens.dangame.rect_ui.Button;
import com.danschellekens.dangame.rect_ui.UIElement;
import com.danschellekens.dantanks.data.Utility;
import com.danschellekens.operations.Increment;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class UtilitySelectorButton extends Button {
	public static final float BORED_SELECTOR_OPACITY = 0f;
	public static final float HOVERED_SELECTOR_OPACITY = 0.1f;
	public static final float HELD_SELECTOR_OPACITY = 0.2f;
	
	Utility utility;
	Image icon;
	
	float selectorOpacity;
	
	public UtilitySelectorButton(UIElement parent, float x, float y, Utility utility) {
		super(parent, new Rectangle(x, y, 80, 40));
		
		this.utility = utility;
		this.icon = utility.getShopInfo().getIcon();
	}

	@Override
	public void update(DanInput input) {
		if (isHeld()) {
			selectorOpacity = HELD_SELECTOR_OPACITY;
		}
		else if (isHovered()) {
			selectorOpacity = Increment.Linear(selectorOpacity, HOVERED_SELECTOR_OPACITY, 0.04f);
		}
		else {
			selectorOpacity = Increment.Linear(selectorOpacity, BORED_SELECTOR_OPACITY, 0.02f);
		}
		
		super.update(input);
	}

	@Override
	protected void renderInPosition(Artist artist) {
		artist.shape().draw(new RoundedRectangle(0, 0, 80, 40, 5), new Color(0, 0, 0, selectorOpacity));
		artist.image().drawCentered(icon, 20, 20);
	}

	public Utility getUtility() {
		return utility;
	}

	public void setUtility(Utility utility) {
		this.utility = utility;
		this.icon = utility.getShopInfo().getIcon();
	}
}

