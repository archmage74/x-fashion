package com.xfashion.client.notepad;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.ui.Panel;

public class ColorFlashAnimation extends Animation {

	Panel panel;
	
	public ColorFlashAnimation(Panel panel) {
		this.panel = panel;
	}
	
	public void run() {
		run(400);
	}
	
	@Override
	protected void onUpdate(double progress) {
		int blue = 255;
		if (progress < 0.5) {
			blue = 255 - (int) (255 * progress);
		} else {
			blue = 128 + (int) (128 * progress);
		}
		panel.getElement().getStyle().setBackgroundColor("rgb(255, 255, " + blue + ")");
	}

	@Override 
	public void onComplete() {
		onUpdate(1.0); 
	}
	
	
}
