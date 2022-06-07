import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Player extends Sprite {
    private List<Projectile> projectiles;

    public Player(int x, int y) {
        super(x, y);
        initPlayer();
    }

    private void initPlayer() {
        projectiles = new ArrayList<>();
        loadImage("src/resources/player.png");
        getImageDimensions();
    }

    public void fire() {
        projectiles.add(new Projectile(x + width, y + height / 2));
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            fire();
        }
    }
}
