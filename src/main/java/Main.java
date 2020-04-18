import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        /*SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Window();
            }
        });*/

        String exprecion = "((1.2+3)*(532.4-2.4 + 4))";
        Calculation.calculate(exprecion);

        //String exprecion = "4.2*526.0";
        //Calculation.calculateExp(exprecion);
        //System.out.println(Calculation.calculateExp(exprecion));
    }
}
