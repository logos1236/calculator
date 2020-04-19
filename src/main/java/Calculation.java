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
    private String exprecion = "";

    private Calculation() {

    }

    public static Calculation singleCalculation() {
        if (calculation == null) {
            calculation = new Calculation();
        }

        return calculation;
    }

    public void reset() {
        this.exprecion = "";
    }

    public void setOperand(String operand) {
        String tmp_exprecion = this.exprecion + operand;
        boolean add_operand = true;
        Matcher matcher = null;

        //=== Проверка точeк
        matcher = Pattern.compile("(\\.{2})|(\\.(\\d)+\\.)|([^\\d]+\\.)|(^\\.)").matcher(tmp_exprecion);
        if (matcher.find()) {
            add_operand = false;
        }

        //=== Проверка операндов
        matcher = Pattern.compile("([\\+\\-\\*\\/\\^]{2})|(^[\\+\\*\\/\\^])").matcher(tmp_exprecion);
        if (matcher.find()) {
            add_operand = false;
        }

        if (add_operand) {
            this.exprecion += operand;
        }
    }

    public String getExprecion() {
        return this.exprecion;
    }

    public void calculateExprecion() throws Exception {
        this.exprecion = calculate(this.exprecion);
    }

    public static String calculate(String exprecion) throws Exception {
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

        String result = calculateExp(exprecion).replace("(", "").replace(")", "").replace(" ", "");

        return result;
    }

    //=== Считаем значение выражения без скобок
    public static String calculateExp(String exprecion) throws Exception {

        LinkedList<String> patterns = new LinkedList();
        for(String operation: Operation.getOperationList()) {
            patterns.add("(^-)?[\\d\\.]+[\\"+operation+"][\\d\\.]+");
        }

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
    public static Double calculateUnitExp(String exprecion) throws Exception {
        //=== Получаем операнды и операцию
        Pattern pattern_digit = Pattern.compile("(^[-]?[\\d\\.]*)|([^\\d\\.])|([\\d\\.]*$)");
        Matcher matcher = pattern_digit.matcher(exprecion);
        int i = 0;
        Double first_operand = null;
        Double second_operand = null;
        String operation = null;
        while (matcher.find()) {
            if (i == 0) {
                first_operand = Double.parseDouble(matcher.group(0).toString());
            }
            if (i == 1) {
                operation = matcher.group(0).toString();
            }
            if (i == 2) {
                second_operand = Double.parseDouble(matcher.group(0).toString());
            }
            i++;
        }

        return Operation.calculate(first_operand, second_operand, operation);
    }
}
