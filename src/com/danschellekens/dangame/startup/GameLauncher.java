package com.danschellekens.dangame.startup;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.util.Log;

import com.danschellekens.dangame.DanGame;
import com.danschellekens.dangame.settings.StartupSettings;

public class GameLauncher {

	public static void Launch(DanGame game) {
		try {
			StartupSettings settings = game.getStartupSettings();
			
			AppGameContainer container = new AppGameContainer(game);
			
			if (settings.isDefaultLoggingEnabled()) {
				Log.setLogSystem(new NullLogSystem());
				Log.setVerbose(false);
			}
	        
			if (settings.getIconDirectory() != null) {
				container.setIcons(new String[] {
					settings.getIconDirectory() + "/32x32.png", 
					settings.getIconDirectory() + "/24x24.png", 
					settings.getIconDirectory() + "/16x16.png"
				} );
			}
			container.setShowFPS(settings.isShowFPS());
			container.setVSync(settings.isVsync());
			container.setDisplayMode(settings.getWidth(), settings.getHeight(), false);
			//container.setAlwaysRender(true);
			//container.setUpdateOnlyWhenVisible(false);
			container.setMultiSample(4);
			
			container.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
