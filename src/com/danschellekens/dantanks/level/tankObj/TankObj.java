package com.danschellekens.dantanks.level.tankObj;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Ellipse;

import com.danschellekens.dantanks.data.Player;
import com.danschellekens.dantanks.data.Tank;
import com.danschellekens.dantanks.exceptions.MissingPlayerException;
import com.danschellekens.dantanks.exceptions.OopsieException;
import com.danschellekens.dantanks.game.LevelUmpire;
import com.danschellekens.dantanks.level.Level;
import com.danschellekens.dantanks.level.shieldObj.ShieldObj;
import com.danschellekens.dantanks.level.world.ground.Ground;
import com.danschellekens.operations.*;
import com.danschellekens.slick2d.graphics.Artist;

public class TankObj {
	static Image graphicColorRight;
	static Image graphicBaseRight;
	static Image graphicColorLeft;
	static Image graphicBaseLeft;
	
	final Tank tank;
	final Level level;
	
	final TankObjHitbox hitbox;
	
	final TankObjHealth health;
	final TankObjFuel fuel;
	
	final float powerMax;
	final float climbMax;
	final float climbMin;
	
	float angle;
	float power;
	
	public TankObj(Tank tank, Level level, Vector2 position) {
		loadGraphics();
		
		this.tank = tank;
		this.level = level;
		
		this.hitbox = new TankObjHitbox(position);
		
		this.health = new TankObjHealth(tank);
		this.fuel = new TankObjFuel(tank);
		
		this.powerMax = tank.getPowerStatValue();
		this.climbMax = tank.getClimbStatValue();
		this.climbMin = tank.getClimbStatValue() * 0.5f;
		
		this.angle = Random.ArrayElement(new Float[] { -70f, 70f });
		this.power = tank.getPowerStatValue() * 0.6f;
	}
	void loadGraphics() {
		try {
			if (graphicColorRight == null) { graphicColorRight = new Image("level/tank/color.png"); }
			if (graphicBaseRight == null) { graphicBaseRight = new Image("level/tank/base.png"); }
			if (graphicColorLeft == null) { graphicColorLeft = graphicColorRight.getFlippedCopy(true, false); }
			if (graphicBaseLeft == null) { graphicBaseLeft = graphicBaseRight.getFlippedCopy(true, false); }
		}
		catch (SlickException e) {
			throw new OopsieException(e);
		}
	}
	
	public void update(LevelUmpire umpire) {
		applyGravity();
		
		if (hasShieldAlgorithm()) {
			getShieldAlgorithm().update();
		}
	}
	boolean applyGravity() {
		Ground ground = this.level.getGround();
		
		Vector2 startPosition = hitbox().getPosition();
		
		float groundHeight = ground.getHighestPointBetween(hitbox.getX1(), hitbox.getX2());
		if (groundHeight - 4 > hitbox.getY2()) { hitbox().changeY(4); }
		else { hitbox().setY(groundHeight + hitbox().getHitboxBottomOffset()); }
		
		// Returns whether or not the tank is settled. (For the settle() method used when first creating the tanks)
		return (startPosition.getY() == hitbox().getPosition().getY());
	}
	public void settle() {
		while (applyGravity() == false) { }
	}
	
	public void driveLeft() {
		// If we don't have enough fuel, don't continue, otherwise consume the fuel.
		if (!fuel().tryConsume(Tank.FUEL_USAGE)) { return; }
		
		Ground ground = level.getGround();
		Vector2 climb = ground.getClimb(hitbox().getX1());
		
		if (climb.getY() > 0) {
			// Climbing up a hill.
			float x = Range.MapConstrain(climb.getY(), climbMin, climbMax, climb.getX() * -Tank.DRIVE_SPEED, 0);
			hitbox().changeX(x); 
		}
		else {
			// Falling down a hill.
			hitbox().changeX(-Tank.DRIVE_SPEED);
		}
	}
	public void driveRight() {
		// If we don't have enough fuel, don't continue, otherwise consume the fuel.
		if (!fuel().tryConsume(Tank.FUEL_USAGE)) { return; }
		
		Ground ground = level.getGround();
		Vector2 climb = ground.getClimb(hitbox().getX2());
		
		if (climb.getY() < 0) {
			// Climbing up a hill.
			float x = Range.MapConstrain(-climb.getY(), climbMin, climbMax, climb.getX() * Tank.DRIVE_SPEED, 0);
			hitbox().changeX(x); 
		}
		else {
			// Falling down a hill.
			hitbox().changeX(Tank.DRIVE_SPEED);
		}
	}
	public boolean canDriveLeft() {
		if (!fuel().canConsume(Tank.FUEL_USAGE)) { return false; }
		
		Ground ground = level.getGround();
		Vector2 climb = ground.getClimb(hitbox().getX1());
		
		if (climb.getY() > 0) { return climb.getY() < climbMax; }
		else { return true; }
	}
	public boolean canDriveRight() {
		if (!fuel().canConsume(Tank.FUEL_USAGE)) { return false; }
		
		Ground ground = level.getGround();
		Vector2 climb = ground.getClimb(hitbox().getX2());
		
		if (climb.getY() < 0) { return -climb.getY() < climbMax; }
		else { return true; }
	}
	
	public void renderGraphics(Artist artist) {
		artist.push();
		artist.translate(Numbers.Round(hitbox().getPosition().getX()), Numbers.Round(hitbox().getPosition().getY()));
		
		if (hasShieldAlgorithm()) {
			getShieldAlgorithm().renderBehind(artist);
		}
		
		Image base = graphicBaseRight;
		Image color = graphicColorRight;
		
		// Flips the tank if facing the other way.
		if (angle < 0) {
			base = graphicBaseLeft;
			color = graphicColorLeft;
		}
		
		try {
			// Draws the colored section of tank.
			Player player = tank.getOwner();
			
			artist.image().drawCentered(base, hitbox().getTextureOffset().getX(), hitbox().getTextureOffset().getY());
			artist.image().drawCentered(color, hitbox().getTextureOffset().getX(), hitbox().getTextureOffset().getY(), player.getColor().getColor());	
		}
		catch (MissingPlayerException e) { throw new OopsieException(e); }
		
		renderBarrel(artist);
		
		if (hasShieldAlgorithm()) {
			getShieldAlgorithm().renderAbove(artist);
		}
		
		artist.pop();
	}
	void renderBarrel(Artist artist) {
		artist.push();
		
		artist.translate(0, -13);
		artist.rotate(angle);
		artist.line().draw(0, 0, 0, -15, new Color(85, 85, 85), 4);
		artist.shape().draw(new Circle(0, 0, 3), new Color(85, 85, 85));
		
		artist.pop();
	}
	public void renderHitboxes(Artist artist) {
		artist.push();
		artist.translate(hitbox().getPosition().getX(), hitbox().getPosition().getY());
		
		try {
			Player player = tank.getOwner();
			
			// Position marker.
			artist.shape().draw(new Ellipse(0, 0, 4, 4), player.getColor().getColor());
				
			// Hit-box
			artist.shape().draw(hitbox().getHitboxDimensions(), null, player.getColor().getColor(), 1); 
		}
		catch (MissingPlayerException e) { throw new OopsieException(e); }
		
		artist.pop();
	}
	
	public Tank getTank() {
		return tank;
	}
	
	public TankObjHealth health() {
		return this.health;
	}
	public TankObjFuel fuel() {
		return this.fuel;
	}
	
	public float getAngle() {
		return angle;
	}
	public void setAngle(float angle) {
		this.angle = Range.Constrain(angle, -90, 90);
	}
	
	public float getPower() {
		return power;
	}
	public void setPower(float power) {
		this.power = Range.Constrain(power, 0, this.powerMax);
	}
	public float getMaxPowerAvailable() {
		return this.powerMax * Tank.GetPowerPercentAvailable(this.health().current(), this.health().max());
	}
	
	public TankObjHitbox hitbox() {
		return this.hitbox;
	}
	public Vector2 getBarrelEndPosition() {
		return hitbox().getBarrelEndPosition(this.angle);
	}
	
	public ShieldObj getShieldAlgorithm() {
		return this.health.getShieldAlgorithm();
	}
	public boolean hasShieldAlgorithm() {
		return this.health.hasShieldAlgorithm();
	}
	public void setShieldAlgorithm(ShieldObj shieldAlgorithm) {
		this.health.setShieldAlgorithm(shieldAlgorithm);
	}
	public void clearShieldAlgorithm() {
		this.health.clearShieldAlgorithm();
	}
}
