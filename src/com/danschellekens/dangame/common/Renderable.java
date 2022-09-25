package com.danschellekens.dangame.common;

import com.danschellekens.slick2d.graphics.Artist;

public interface Renderable {
	void renderGraphic(Artist artist);
	void renderHitbox(Artist artist);
}
