package com.xfashion.client;

import com.google.gwt.animation.client.Animation;

public class PanelWidthAnimation extends Animation {

	private PanelAnimData anim;
	
	public PanelWidthAnimation(IsMinimizable fp, int start, int end) {
		anim = new PanelAnimData(fp, start, end);
	}
	
	@Override
	protected void onUpdate(double progress) {
		int current = anim.startWidth + (int) ((anim.endWidth - anim.startWidth) * progress);
		anim.panel.setWidth(current);
	}
	
	@Override
	protected void onComplete() {
		anim.panel.setWidth(anim.endWidth);
		anim.panel.setMinimized(anim.endWidth < anim.startWidth);
	}

	public class PanelAnimData {
		public IsMinimizable panel;
		public int startWidth;
		public int endWidth;
		public PanelAnimData(IsMinimizable p, int s, int e) {
			this.panel = p;
			this.startWidth = s;
			this.endWidth = e;
		}
	}
	
}
