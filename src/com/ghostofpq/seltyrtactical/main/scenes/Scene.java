package java.com.ghostofpq.seltyrtactical.main.scenes;

/**
 * Created with IntelliJ IDEA.
 * User: GhostOfPQ
 * Date: 26/06/13
 * Time: 19:04
 * To change this template use File | Settings | File Templates.
 */
public interface Scene {


    public void init();

    public void update();

    public void render();

    public void manageInput();
}
