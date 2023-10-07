import javax.swing.*;

public class SnakeFrame extends JFrame {
    public SnakeFrame() {
        SnakePanel panel = new SnakePanel();
        add(panel);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Level");
        JMenuItem easy = new JMenuItem("Easy");
        easy.addActionListener(event -> {
            SnakePanel.setDelay(400);
            panel.reset();
        });
        menu.add(easy);

        JMenuItem medium = new JMenuItem("Medium");
        medium.addActionListener(event -> {
            SnakePanel.setDelay(200);
            panel.reset();
        });
        menu.add(medium);

        JMenuItem hard = new JMenuItem("Hard");
        hard.addActionListener(event -> {
            SnakePanel.setDelay(100);
            panel.reset();
        });
        menu.add(hard);

        JMenuItem expert = new JMenuItem("Expert");
        expert.addActionListener(event -> {
            SnakePanel.setDelay(50);
            panel.reset();
        });
        menu.add(expert);

        menuBar.add(menu);

        JMenu theme = new JMenu("Theme");
        JMenuItem theme1 = new JMenuItem("Black and White");
        theme1.addActionListener(event -> {
            panel.setTheme(new BlackAndWhiteTheme());
            panel.reset();
        });
        theme.add(theme1);

        JMenuItem theme2 = new JMenuItem("Negative");
        theme2.addActionListener(event -> {
            panel.setTheme(new NegativeTheme());
            panel.reset();
        });
        theme.add(theme2);

        JMenuItem theme3 = new JMenuItem("Classic");
        theme3.addActionListener(event -> {
            panel.setTheme(new HighContrastTheme());
            panel.reset();
        });
        theme.add(theme3);

        menuBar.add(theme);

        setJMenuBar(menuBar);

        setTitle("Snake Game - Growing Serpent Adventure");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
