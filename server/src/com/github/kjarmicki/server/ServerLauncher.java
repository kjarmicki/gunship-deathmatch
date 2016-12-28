package com.github.kjarmicki.server;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.github.kjarmicki.client.GunshipDeathmatchClient;
import com.github.kjarmicki.server.debug.GunshipDeathmatchServerRendered;
import com.github.kjarmicki.util.Env;

public class ServerLauncher {
    public static void main(String[] args) {
        Env env = new Env(System.getenv());

        if(env.inDebugMode()) {
            System.out.println("debug");
            LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
            config.width = 1000;
            config.height = 1000;
            new LwjglApplication(new GunshipDeathmatchServerRendered(), config);
            return;
        }

        new HeadlessApplication(new GunshipDeathmatchServer());
    }
}
