public class Enemy extends Sprite {
    private final double speed = (Math.random() * 2) + 1;
    private double angle;
    private int points;

    public Enemy(int x, int y) {
        super(x, y);

        initEnemy();
    }

    private void initEnemy() {
        int pt = (int) (Math.random() * 100) + 1;
        if (pt <= 80) {
            points = 25;
            loadImage("resources/enemy.png");
            getImageDimensions();
        } else if (pt > 80 && pt <= 98) {
            points = 50;
            loadImage("resources/enemy2.png");
            getImageDimensions();
        } else {
            points = 100;
            loadImage("resources/enemy3.png");
            getImageDimensions();
        }
    }

    public int getPoints() {
        return points;
    }

    public void move(double pY, double pX) {
        if (x <= pX && y <= pY) {
            angle = (double) Math.atan2(pY - y, pX - x);
            x += (double) (Math.cos(angle) * speed);
            y += (double) (Math.sin(angle) * speed);
        } else if (x >= pX && y >= pY) {
            angle = (double) Math.atan2(y - pY, x - pX);
            x -= (double) (Math.cos(angle) * speed);
            y -= (double) (Math.sin(angle) * speed);
        } else if (x <= pX && y >= pY) {
            angle = (double) Math.atan2(y - pY, pX - x);
            x += (double) (Math.cos(angle) * speed);
            y -= (double) (Math.sin(angle) * speed);
        } else if (x >= pX && y <= pY) {
            angle = (double) Math.atan2(pY - y, x - pX);
            x -= (double) (Math.cos(angle) * speed);
            y += (double) (Math.sin(angle) * speed);
        } else {
            return;
        }
    }
}
