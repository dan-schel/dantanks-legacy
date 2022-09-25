package com.danschellekens.dantanks.ui.standingUpdate;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

import com.danschellekens.dangame.rect_ui.*;
import com.danschellekens.dantanks.data.Player;
import com.danschellekens.dantanks.page.StandingsUpdatePage;
import com.danschellekens.dantanks.ui.FontLibrary;
import com.danschellekens.operations.*;
import com.danschellekens.slick2d.graphics.Artist;
import com.danschellekens.slick2d.input.DanInput;

public class PlayerScoreUI extends Panel {
	public static final int WIDTH = 800;
	public static final int BARS_START_X = 200;
	public static final int BARS_END_X = 600;
	public static final int BARS_WIDTH = BARS_END_X - BARS_START_X;
	public static final int HEIGHT = 50;
	
	final Player player;
	final int initialScore;
	final int pointsAwarded;
	final int graphMaxDomain;
	
	int animationTick;
	float pointsAwardedHighlightOpacity;
	float originalScoreDisplayValue;
	float awardedPointsDisplayValue;
	
	Label playerNameLabel;
	Label scoresLabel;
	
	public PlayerScoreUI(UIElement parent, int x, int y, Player player, int initialScore, int pointsAwarded, int graphMaxDomain) {
		super(parent, new Rectangle(x, y, WIDTH, HEIGHT));
		this.player = player;
		this.initialScore = initialScore;
		this.pointsAwarded = pointsAwarded;
		this.graphMaxDomain = graphMaxDomain;
		
		originalScoreDisplayValue = 0;
		awardedPointsDisplayValue = 0;
		pointsAwardedHighlightOpacity = 1;
		
		playerNameLabel = new Label(this, new Rectangle(20, 10, BARS_START_X - 40, HEIGHT - 20), FontLibrary.getSubtitleFont());
		playerNameLabel.setAlign(VAlign.CENTER, HAlign.RIGHT);
		playerNameLabel.setColor(new Color(1f, 1f, 1f, 1f));
		playerNameLabel.setText(player.getName());
		this.addChild(playerNameLabel);
		
		scoresLabel = new Label(this, new Rectangle(0, 10, 0, HEIGHT - 20), FontLibrary.getBodyFont());
		scoresLabel.setAlign(VAlign.CENTER, HAlign.LEFT);
		scoresLabel.setColor(new Color(1f, 1f, 1f, 0f));
		scoresLabel.setText("+ " + pointsAwarded + " pts"); 
		this.addChild(scoresLabel);
		
		animationTick = 0;
	}

	@Override
	public void update(DanInput input) {
		animationTick++;
		if (animationTick > StandingsUpdatePage.ANIMATION_LENGTH_PHASE_1) {
			awardedPointsDisplayValue = Increment.Exponential(awardedPointsDisplayValue, Numbers.Larger(graphMaxDomain / (BARS_WIDTH * 0.25f), pointsAwarded), 0.25f);
		}
		originalScoreDisplayValue = Increment.Exponential(originalScoreDisplayValue, Numbers.Larger(graphMaxDomain / (BARS_WIDTH * 0.25f), initialScore), 0.25f);
		pointsAwardedHighlightOpacity = Range.MapConstrain(
			animationTick, 
			StandingsUpdatePage.ANIMATION_LENGTH_PHASE_1 + StandingsUpdatePage.ANIMATION_LENGTH_PHASE_2, 
			StandingsUpdatePage.ANIMATION_LENGTH_PHASE_1 + StandingsUpdatePage.ANIMATION_LENGTH_PHASE_2 + StandingsUpdatePage.ANIMATION_LENGTH_PHASE_3, 
			1, 
			0.4f
		);
		
		scoresLabel.setX(BARS_START_X + getBarWidth() + 20);
		scoresLabel.setColor(new Color(1f, 1f, 1f, Range.MapConstrain(pointsAwardedHighlightOpacity, 0.4f, 1, 1, 0)));
		
		super.update(input);
	}

	@Override
	protected void renderInPosition(Artist artist) {
		float originalScoreBarWidth = Range.Map(originalScoreDisplayValue, 0, graphMaxDomain, 0, BARS_WIDTH);
		artist.shape().draw(new Rectangle(BARS_START_X, 10, originalScoreBarWidth, HEIGHT - 20), player.getColor().getColor());

		float awardedPointsBarWidth = Range.Map(awardedPointsDisplayValue, 0, graphMaxDomain, 0, BARS_WIDTH);
		artist.shape().draw(new Rectangle(BARS_START_X + originalScoreBarWidth, 10, awardedPointsBarWidth, HEIGHT - 20), player.getColor().getColor());
		artist.shape().draw(new Rectangle(BARS_START_X + originalScoreBarWidth, 10, awardedPointsBarWidth, HEIGHT - 20), new Color(1f, 1f, 1f, pointsAwardedHighlightOpacity));
		
		artist.shape().draw(new Rectangle(BARS_START_X - 2, 0, 2, HEIGHT), new Color(1f, 1f, 1f, 0.5f));
		
		super.renderInPosition(artist);
	}
	
	public float getBarWidth() {
		float originalScoreBarWidth = Range.Map(originalScoreDisplayValue, 0, graphMaxDomain, 0, BARS_WIDTH);
		float awardedPointsBarWidth = Range.Map(awardedPointsDisplayValue, 0, graphMaxDomain, 0, BARS_WIDTH);
		return originalScoreBarWidth + awardedPointsBarWidth;
	}
}
