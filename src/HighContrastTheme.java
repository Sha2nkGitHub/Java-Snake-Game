import java.awt.*;

public class HighContrastTheme extends Theme {
    public HighContrastTheme() {
        background = Color.BLACK;
        snakeHead = Color.GREEN;
        snakeBody = new Color(0, 200, 0);
        foodBody = Color.RED;
        foodEyes = Color.WHITE;
        foodNose = Color.BLACK;
        eyeColor = Color.RED;
        borderColor = Color.BLUE;
    }
}
