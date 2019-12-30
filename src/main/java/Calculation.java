import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class Calculation {
    private static Calculation calculation = null;

    private char operation;
    private Float operand = null;

    private Float result = null;
    private String result_str = "";

    private Calculation() {

    }

    public static Calculation singleCalculation() {
        if (calculation == null) {
            calculation = new Calculation();
        } else {
            calculation.operation = '\u0000';
            calculation.operand = null;

            calculation.result = null;
            calculation.result_str = "";
        }

        return calculation;
    }

    public void setOperation(char operation) {
        this.operation = operation;

        this.result = this.operand;
        this.result_str = "";

        operand = null;
    }

    public void setOperand(String operand) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        DecimalFormat format = new DecimalFormat("0.#");
        format.setDecimalFormatSymbols(symbols);

        if (operand.equals(".")) {
            if (!this.result_str.contains(".")) {
                if (this.result_str.equals("")) {
                    this.result_str += "0.";
                } else {
                    this.result_str += ".";
                }
            }
        } else {
            this.result_str += operand;
        }

        try {
            this.operand = format.parse(this.result_str).floatValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public float getOperand() {
        return this.operand;
    }

    public String getResultStr() {
        return this.result_str;
    }

    public static String getFloatStr(float operand) {
        String operand_str = String.valueOf(operand);

        if (operand % 1 == 0) {
            operand_str = String.format("%.0f", operand);
        }

        return operand_str;
    }

    public void calculation () {
        switch (operation) {
            case '+': result += operand;
                break;
            case '-': result -= operand;
                break;
        }

        System.out.println(result);

        operand = result;
        result_str = "";
    }

    public float getResult() {
        return this.result;
    }
}
