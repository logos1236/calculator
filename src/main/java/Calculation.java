public class Calculation {
    private static Calculation calculation = null;

    private char operation;
    private Float operand = null;
    private Float result = null;

    private Calculation() {

    }

    public static Calculation singleCalculation() {
        if (calculation == null) {
            calculation = new Calculation();
        } else {
            calculation.operation = '\u0000';
            calculation.operand = null;
            calculation.result = null;
        }

        return calculation;
    }

    public void setOperation(char operation) {
        this.operation = operation;

        result = operand;

        operand = null;
    }

    public void setOperand(float operand) {
        if (this.operand == null) {
            this.operand = operand;
        } else {
            this.operand = Float.parseFloat(String.valueOf(this.operand)+String.valueOf(operand));
        }
    }

    public float getOperand() {
        return this.operand;
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

        operand = result;
    }

    public float getResult() {
        return this.result;
    }
}
