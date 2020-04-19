import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Window();
            }
        });

        /*String exprecion = "((1.2+3)*(532.4-2.4 + 4))";
        try {
            Calculation.calculate(exprecion);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
