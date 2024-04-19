import java.awt.*;

public class Projectile extends Sprite {
    private final double speed = 10;
    private double angle;
    private Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

    public Projectile(double x, double y, double mX, double mY) {
        super(x, y);
        angle = (double) Math.atan2(mY - y, mX - x);

        initProjectile();
    }

    private void initProjectile() {
        loadImage("resources/projectile.png");
        getImageDimensions();
    }

    public void move() {
        if (x > size.getWidth() || x < 0 || y < 0 || y > size.getHeight()) {
            visible = false;
        } else {
            x += (double) (speed * Math.cos(angle));
            y += (double) (speed * Math.sin(angle));
        }
    }
}