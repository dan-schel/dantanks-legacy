package com.danschellekens.dantanks.ui.shop;

import com.danschellekens.dangame.rect_ui.UIElement;
import com.danschellekens.dantanks.data.values.ShopCategory;
import com.danschellekens.dantanks.ui.control.ColorBarButton;

public class UtilityCategoryButton extends ColorBarButton {
	ShopCategory category;
	
	public UtilityCategoryButton(UIElement parent, int x, int y, int w, ShopCategory category) {
		super(parent, x, y, w);
		
		this.category = category;
		setText(category.getDisplayName());
	}
	
	public ShopCategory getCategory() {
		return category;
	}
}
