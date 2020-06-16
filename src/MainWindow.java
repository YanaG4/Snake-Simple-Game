import javax.swing.*;

import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(370, 380);
        setLocation(400, 400);
        add(new GameField());
        setVisible(true);
    }
}
