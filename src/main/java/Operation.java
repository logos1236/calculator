import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Operation {
    private static LinkedList<String> operation_list = new LinkedList<>();
    static {
        operation_list.add("^");
        operation_list.add("*");
        operation_list.add("/");
        operation_list.add("+");
        operation_list.add("-");
    }

    public static LinkedList<String> getOperationList() {
        return operation_list;
    }

    public static Double calculate(Double first_operand, Double second_operand, String operation) throws Exception {
        Double result = null;

        if (!operation_list.contains(operation)) {
            throw new Exception("Can't find operation: "+operation);
        }

        if (operation.equals("^")) {
            result = Math.pow(first_operand, second_operand);
        }
        if (operation.equals("*")) {
            result = first_operand * second_operand;
        }
        if (operation.equals("/")) {
            result = first_operand / second_operand;
        }
        if (operation.equals("+")) {
            result = first_operand + second_operand;
        }
        if (operation.equals("-")) {
            result = first_operand - second_operand;
        }

        return result;
    }

    public static boolean validateExprecion(String exprecion) throws Exception {
        String result_str = null;
        boolean result = true;
        Matcher matcher = null;

        //=== Проверка точeк
        matcher = Pattern.compile("(\\.{2})|(\\.(\\d)+\\.)|([^\\d]+\\.)|(^\\.)").matcher(exprecion);
        if (matcher.find()) {
            result_str = "Неверно расставлены точки";
            result = false;
        }

        //=== Проверка операндов
        matcher = Pattern.compile("([\\+\\-\\*\\/\\^]{2})|(^[\\+\\*\\/\\^])").matcher(exprecion);
        if (matcher.find()) {
            result_str = "Неверно расставлены операнды";
            result = false;
        }

        //=== Проверка скобок
        int first_parentheses = -1;
        int last_parentheses = -1;
        first_parentheses = exprecion.lastIndexOf('(');
        last_parentheses = exprecion.lastIndexOf(')');

        if (last_parentheses == 0) {
            result = false;
            result_str = "Неверно расставлены скобки";
        }
        if ((last_parentheses > 0) && (last_parentheses < first_parentheses)) {
            result = false;
            result_str = "Неверно расставлены скобки";
        }
        System.out.println("first_parentheses "+first_parentheses);
        System.out.println("last_parentheses "+last_parentheses);

        if (!result) {
            throw new Exception(result_str);
        }

        return result;
    }
}
