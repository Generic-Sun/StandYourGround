public class Projectile extends Sprite {
    private final int speed = 2;
    public Projectile(int x, int y) {
        super(x, y);
        initProjectile();
    }
    private void initProjectile() {
        loadImage("src/resources/projectile.png");
        getImageDimensions();
    }
    public void move(float mX, float mY) {
        float angle = (float)Math.atan2 ( mY - y, mX - x );
        x += (float) (Math.cos(angle) * speed);
        y += (float) (Math.sin(angle) * speed);
        if (x > 1280 || x < 0 || y < 0 || y > 720) {
            visible = false;
        }
    }
}