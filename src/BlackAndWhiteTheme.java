import java.awt.*;

public class BlackAndWhiteTheme extends Theme {
    public BlackAndWhiteTheme() {
        background = Color.BLACK;
        snakeHead = Color.WHITE;
        snakeBody = new Color(220, 220, 220);
        foodBody = Color.WHITE;
        foodEyes = Color.BLUE;
        foodNose = Color.BLACK;
        eyeColor = Color.BLACK;
        borderColor = Color.GRAY;
    }
}
