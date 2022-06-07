import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Board extends JPanel implements ActionListener {
    private Timer timer;
    private int points;
    private Player player;
    private List<Enemy> enemies;
    private boolean inGame;
    private final int width = 1280;
    private final int height = 720;
    private final int delay = 15;
    public Board() {

        initBoard();
    }

    private void initBoard() {
        setFocusable(true);
        setBackground(Color.black);
        inGame = true;

        setPreferredSize(new Dimension(width, height));

        player = new Player(600, 325);

        initEnemies();

        timer = new Timer(delay, this);
        timer.start();
    }

    public void initEnemies() {
        enemies = new ArrayList<>();

        for (int i = 0; i < 20; i ++) {
            int j = (int) (Math.random() * 4 + 1);
            if (j == 1) {
                enemies.add(new Enemy( (int) (Math.random() * 1280), (int) (Math.random() * -500)));
            } else if (j == 2) {
                enemies.add(new Enemy( (int) (Math.random() * 1280), (int) (Math.random() * 1220) + 720));
            } else if (j == 3) {
                enemies.add(new Enemy((int) (Math.random() * 1780) + 1280, (int) (Math.random() * 720)));
            } else if (j == 4) {
                enemies.add(new Enemy((int) (Math.random() * -500), (int) (Math.random() * 720)));
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (inGame) {
            drawObjects(g);
        } else {
            drawGameOver(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics g) {
        if (player.isVisible()) {
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        List<Projectile> p = player.getProjectiles();

        for (Projectile pr : p) {
            if (pr.isVisible()) {
                g.drawImage(pr.getImage(), pr.getX(), pr.getY(),this);
            }
        }

        for (Enemy e : enemies) {
            if (e.isVisible()) {
                g.drawImage(e.getImage(), e.getX(), e.getY(), this);
            }
        }

        g.setColor(Color.WHITE);
        g.drawString("Score: " + points, 5, 15);
    }

    private void drawGameOver(Graphics g) {
        String str = "Game Over";
        Font f = new Font("Helvetica", Font.BOLD, 24);
        FontMetrics fm = getFontMetrics(f);

        g.setColor(Color.WHITE);
        g.setFont(f);
        g.drawString(str, (width - fm.stringWidth(str)) / 2, height / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        inGame();

        updateProjectiles();
        updateEnemies();

        checkCollision();

        repaint();
    }

    private void inGame() {
        if (!inGame) {
            timer.stop();
        }
    }

    private void updateProjectiles() {
        List<Projectile> p = player.getProjectiles();

        for (int i = 0; i < p.size(); i ++) {
            Projectile pr = p.get(i);

            if (pr.isVisible()) {
                pr.move(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
            } else {
                p.remove(i);
            }
        }
    }

    private void updateEnemies() {
        for (int i = 0; i < enemies.size(); i ++) {
            Enemy e = enemies.get(i);

            if (e.isVisible()) {
                e.move(player.getX(), player.getY());
            } else {
                enemies.remove(i);
            }
        }
    }

    private void checkCollision() {
        Rectangle r3 = player.getBounds();

        for (Enemy e : enemies) {

            Rectangle r2 = e.getBounds();

            if (r2.intersects(r3)) {

                player.setVisible(false);
                e.setVisible(false);
                inGame = false;
            }
        }

        List<Projectile> p = player.getProjectiles();

        for (Projectile pr : p) {

            Rectangle r1 = pr.getBounds();

            for (Enemy e : enemies) {

                Rectangle r2 = e.getBounds();

                if (r1.intersects(r2)) {

                    points += 50;
                    pr.setVisible(false);
                    for (int i = 0; i < 20; i ++) {
                        int j = (int) (Math.random() * 4 + 1);
                        if (j == 1) {
                            e.setPosition((int) (Math.random() * 1280), (int) (Math.random() * -500));
                        } else if (j == 2) {
                            e.setPosition( (int) (Math.random() * 1280), (int) (Math.random() * 1220) + 720);
                        } else if (j == 3) {
                            e.setPosition((int) (Math.random() * 1780) + 1280, (int) (Math.random() * 720));
                        } else if (j == 4) {
                            e.setPosition((int) (Math.random() * -500), (int) (Math.random() * 720));
                        }
                    }
                }
            }
        }
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
        }
    }
}
