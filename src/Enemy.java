public class Enemy extends Sprite {
    private int speed = 2;
    public Enemy(int x, int y) {
        super(x, y);
        initEnemy();
    }
    private void initEnemy() {
        loadImage("src/resources/enemy.png");
        getImageDimensions();
    }
    public void move(float pX, float pY) {
        float angle = (float)Math.atan2 ( pY - y, pX - x );
        x += (float) (Math.cos(angle) * speed);
        y += (float) (Math.sin(angle) * speed);
    }
}
