
import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main extends JFrame {

    public Main() {
        initMain();
    }

    public void initMain() {

        add(new Board());
        setResizable(true);
        pack();

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Stand Your Ground!");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Main main = new Main();
            main.setVisible(true);
        });
    }

}