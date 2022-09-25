package com.danschellekens.dantanks.page;

import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public abstract class Page {
	public Page() {
		
	}
	
	public abstract void init();
	public abstract void update(DanInput input);
	public abstract void render(Artist artist);
}
