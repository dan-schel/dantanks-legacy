package com.danschellekens.dantanks.data;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.danschellekens.dantanks.data.values.ShopCategory;
import com.danschellekens.dantanks.exceptions.OopsieException;

public class UtilityShopInfo {
	final String displayName;
	final String displayNamePlural;
	String description;
	Image icon;
	int priceOfPack;
	int quantityInPack;
	ShopCategory category;
	
	public UtilityShopInfo(String displayName, String displayNamePlural) {
		this.displayName = displayName;
		this.displayNamePlural = displayNamePlural;
		this.description = "Does something, I'd assume.";
		this.priceOfPack = 0;
		this.quantityInPack = 0;
		this.category = ShopCategory.MISCELLENEOUS;
		
		try {
			this.icon = new Image("utility/unknown.png");
		} 
		catch (SlickException e) {
			throw new OopsieException(e);
		}
	}
	
	public String getDisplayName() {
		return displayName;
	}
	public String getDisplayNamePlural() {
		return displayNamePlural;
	}
	public String getRelevantDisplayName(int quantity) {
		if (quantity == 1) {
			return getDisplayName();
		}
		else {
			return getDisplayNamePlural();
		}
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPriceOfPack() {
		return priceOfPack;
	}
	public void setPriceOfPack(int priceOfPack) {
		this.priceOfPack = priceOfPack;
	}
	public int getQuantityInPack() {
		return quantityInPack;
	}
	public void setQuantityInPack(int quantityInPack) {
		this.quantityInPack = quantityInPack;
	}
	public ShopCategory getCategory() {
		return category;
	}
	public void setCategory(ShopCategory category) {
		this.category = category;
	}
	public Image getIcon() {
		return icon;
	}
	public void setIcon(Image icon) {
		this.icon = icon;
	}
}
