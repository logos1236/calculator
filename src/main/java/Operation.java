import java.util.LinkedList;

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
}
