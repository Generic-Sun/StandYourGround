import java.awt.*;
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
        loadImage("resources/player.png");
        getImageDimensions();
    }

    public void fire() {
        projectiles.add(new Projectile(x + (width / 2), y + (height / 2),
                (float) MouseInfo.getPointerInfo().getLocation().getX(),
                (float) MouseInfo.getPointerInfo().getLocation().getY()));
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public void mousePressed(MouseEvent e) {

        int key = e.getButton();

        if (key == MouseEvent.BUTTON1) {
            fire();
        }
    }
}
