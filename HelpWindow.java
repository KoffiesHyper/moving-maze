import java.awt.Font;
import java.awt.Color;

public class HelpWindow{

    private static String[] bindings = {
        "w - Move up",
        "s - Move down",
        "a - Move left",
        "d - Move right",
        "l - Rotate floating tile counter-clockwise",
        "r - Rotate floating tile clockwise",
        "mouse-click - Slide tile",
        "f - Finish moving turn",
        "q - Quit the game"
    };
 
    public static void showHelpWindow(){
        Draw window = new Draw();
        window.enableDoubleBuffering();

        window.setCanvasSize(350, 400);
        window.setXscale(0, 350);
        window.setYscale(0, 400);

        window.clear(new Color(49, 72, 72));
        window.setPenColor(Draw.WHITE);
        window.setTitle("Moving Maze - Key Bindings");

        Font title = new Font("Monospaced", Font.BOLD, 20); 
        Font bindingFont = new Font("Monospaced", Font.PLAIN, 12);

        window.setFont(title);
        window.setPenRadius(.0025);
        window.text(350/2, 360, "Key Bindings");

        window.setFont(bindingFont);
        for(int i = 0; i < bindings.length; i++){
            window.textLeft(30, 325 - 30*i, bindings[i]);
            window.line(30, 325 - 30*i - 13, 320, 325 - 30*i - 13);
        }

        window.show();
    }
}