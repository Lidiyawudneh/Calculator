import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        System.out.println("lets do some calculation");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter your Equation (type 'done' to quit):");
            String operation = scanner.nextLine();

            if (operation.equalsIgnoreCase("done")) {
                break;
            }

            try {
                int result = evaluateOperation(operation);
                System.out.println("Result: " + result);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    public static int evaluateOperation(String operation) {
        operation = operation.replaceAll("\\s+", ""); // Remove spaces from the operation

        if (!operation.matches("([1-9]|10)(([-+*/])([1-9]|10))+|[1-9]|10")) {
            throw new IllegalArgumentException("Invalid arithmetic operation or Number is Out of Range");
        }

        int result = 0;
        char operator = '+';
        int startIndex = 0;

        for (int i = 0; i < operation.length(); i++) {
            char ch = operation.charAt(i);

            if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                int operand = parseNumber(operation.substring(startIndex, i));
                result = applyOperator(result, operand, operator);
                operator = ch;
                startIndex = i + 1;
            }
        }

        int lastOperand = parseNumber(operation.substring(startIndex));
        result = applyOperator(result, lastOperand, operator);

        return result;
    }

    private static int parseNumber(String str) {
        try {
            int number = Integer.parseInt(str);
            if (number < 1 || number > 10) {
                throw new IllegalArgumentException("Invalid number: " + number);
            }
            return number;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number: " + str);
        }
    }

    private static int applyOperator(int operand1, int operand2, char operator) {
        if (operator == '+') {
            return operand1 + operand2;
        } else if (operator == '-') {
            return operand1 - operand2;
        } else if (operator == '*') {
            return operand1 * operand2;
        } else if (operator == '/') {
            if (operand2 == 0) {
                throw new IllegalArgumentException("Division by zero is not allowed");
            }
            return operand1 / operand2;
        }
        throw new IllegalArgumentException("Invalid operator: " + operator);
    }
}