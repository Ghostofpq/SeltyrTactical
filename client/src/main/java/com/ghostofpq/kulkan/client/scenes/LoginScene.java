package com.ghostofpq.kulkan.client.scenes;

public class LoginScene implements Scene {

    private static volatile LoginScene instance = null;

    private LoginScene() {
    }

    public static LoginScene getInstance() {
        if (instance == null) {
            synchronized (LoginScene.class) {
                if (instance == null) {
                    instance = new LoginScene();
                }
            }
        }
        return instance;
    }

    @Override
    public void init() {
    }

    @Override
    public void update(long deltaTime) {
    }

    @Override
    public void render() {
    }

    @Override
    public void manageInput() {
    }
}
