import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static void calculate(String exprecion) {
        System.out.println(exprecion);

        //=== Расскрываем скобки в выражении
            int first_parentheses = exprecion.lastIndexOf('(');
            while (first_parentheses > 0) {
                int last_parentheses = first_parentheses + exprecion.substring(first_parentheses).indexOf(')') + 1;
                String substr = exprecion.substring(first_parentheses, last_parentheses).replace("(", "").replace(")", "").replace(" ", "");
                String expr_result = calculateExp(substr);
                exprecion = new StringBuilder(exprecion).replace(first_parentheses, last_parentheses, expr_result.toString()).toString();
                System.out.println(exprecion);

                first_parentheses = exprecion.lastIndexOf('(');
            }

        exprecion = calculateExp(exprecion).replace("(", "").replace(")", "").replace(" ", "");
        System.out.println(exprecion);
    }

    //=== Считаем значение выражения без скобок
    public static String calculateExp(String exprecion) {
        LinkedList<String> patterns = new LinkedList();
        patterns.add("(^-)?[\\d\\.]+[\\^][\\d\\.]+");
        patterns.add("(^-)?[\\d\\.]+[\\*][\\d\\.]+");
        patterns.add("(^-)?[\\d\\.]+[\\/][\\d\\.]+");
        patterns.add("(^-)?[\\d\\.]+[\\+][\\d\\.]+");
        patterns.add("(^-)?[\\d\\.]+[-][\\d\\.]+");
        Matcher matcher = null;
        boolean repeat_calculate = true;

        while(repeat_calculate) {
            repeat_calculate = false;

            for (String pattern : patterns) {
                matcher = Pattern.compile(pattern).matcher(exprecion);
                if (matcher.find()) {
                    //=== Считаем значение полученного выражения
                    Double matcher_res = calculateUnitExp(matcher.group(0));
                    exprecion = Pattern.compile(pattern).matcher(exprecion).replaceFirst(matcher_res.toString());
                    repeat_calculate = true;
                    break;
                }
            }
        }

        return exprecion;
    }
    //=== Считаем значение элементарного выражения
    public static Double calculateUnitExp(String exprecion) {
        Pattern pattern_digit = Pattern.compile("(^[-]?[\\d\\.]*)|([^\\d\\.])|([\\d\\.]*$)");
        Matcher matcher = null;
        matcher = pattern_digit.matcher(exprecion);
        ArrayList expr_arr = new ArrayList();
        Double expr_result = 0.0;
        while (matcher.find()) {
            //System.out.println(matcher.group(0));
            expr_arr.add(matcher.group(0));
        }

        if (expr_arr.get(1).equals("^")) {
            expr_result = Math.pow(Double.parseDouble(expr_arr.get(0).toString()), Double.parseDouble(expr_arr.get(2).toString()));
        }
        if (expr_arr.get(1).equals("*")) {
            expr_result = Double.parseDouble(expr_arr.get(0).toString()) * Double.parseDouble(expr_arr.get(2).toString());
        }
        if (expr_arr.get(1).equals("/")) {
            expr_result = Double.parseDouble(expr_arr.get(0).toString()) / Double.parseDouble(expr_arr.get(2).toString());
        }
        if (expr_arr.get(1).equals("+")) {
            expr_result = Double.parseDouble(expr_arr.get(0).toString()) + Double.parseDouble(expr_arr.get(2).toString());
        }
        if (expr_arr.get(1).equals("-")) {
            expr_result = Double.parseDouble(expr_arr.get(0).toString()) - Double.parseDouble(expr_arr.get(2).toString());
        }

        return expr_result;
    }
}
