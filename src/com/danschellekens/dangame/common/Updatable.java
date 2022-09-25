package com.danschellekens.dangame.common;

import com.danschellekens.slick2d.input.DanInput;

public interface Updatable {
	void updatePlaying(DanInput input);
	void updatePaused(DanInput input);
}
