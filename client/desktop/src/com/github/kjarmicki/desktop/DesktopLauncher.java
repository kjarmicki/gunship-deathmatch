package com.github.kjarmicki.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.github.kjarmicki.GunshipDeathmatch;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 900;
		config.height = 900;
		new LwjglApplication(new GunshipDeathmatch(), config);
	}
}
