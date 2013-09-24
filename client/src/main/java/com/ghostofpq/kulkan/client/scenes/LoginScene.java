package com.ghostofpq.kulkan.client.scenes;

import com.ghostofpq.kulkan.client.Client;
import com.ghostofpq.kulkan.client.graphics.PasswordField;
import com.ghostofpq.kulkan.client.graphics.TextField;
import com.ghostofpq.kulkan.client.utils.GraphicsManager;
import lombok.extern.slf4j.Slf4j;
import org.lwjgl.input.Mouse;

@Slf4j
public class LoginScene implements Scene {

    private static volatile LoginScene instance = null;
    private TextField pseudo;
    private PasswordField password;

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
        pseudo = new TextField(200, 200, 300, 50, 10);
        password = new PasswordField(200, 300, 300, 50, 10);
        pseudo.setHasFocus(true);
    }

    @Override
    public void update(long deltaTime) {
    }

    @Override
    public void render() {
        GraphicsManager.getInstance().make2D();
        pseudo.draw();
        password.draw();
    }

    @Override
    public void manageInput() {
        while (Mouse.next()) {
            if (Mouse.isButtonDown(0)) {
                log.debug("Clic : {}/{}", Mouse.getX(), Mouse.getY());
                if (pseudo.isClicked(Mouse.getX(), Client.getInstance().getHeight() - Mouse.getY())) {
                    pseudo.setHasFocus(true);
                    password.setHasFocus(false);
                }
                if (password.isClicked(Mouse.getX(), Client.getInstance().getHeight() - Mouse.getY())) {
                    password.setHasFocus(true);
                    pseudo.setHasFocus(false);
                }
            }
        }


    }
}
