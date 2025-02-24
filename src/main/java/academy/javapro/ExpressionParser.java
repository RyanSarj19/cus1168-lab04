package academy.javapro;

class ExpressionParser {
    private final String input;
    private int position;

    public ExpressionParser(String input) {
        this.input = input;
        this.position = 0;
    }

    public double parseExpression() {
        double value = parseTerm();
        while (!endOfInput() && currentChar() == '+') {
            position++;
            value += parseTerm();
        }
        return value;
    }

    private double parseTerm() {
        double value = parseFactor();
        while (!endOfInput() && currentChar() == '*') {
            position++;
            value *= parseFactor();
        }
        return value;
    }

    private double parseFactor() {
        if (!endOfInput() && currentChar() == '(') {
            position++;
            double result = parseExpression();
            if (!endOfInput() && currentChar() == ')') {
                position++;
            } else {
                throw new RuntimeException("Missing closing parenthesis");
            }
            return result;
        }
        return parseNumber();
    }

    private double parseNumber() {
        StringBuilder sb = new StringBuilder();
        while (!endOfInput() && (Character.isDigit(currentChar()) || currentChar() == '.')) {
            sb.append(currentChar());
            position++;
        }
        if (sb.length() == 0) {
            throw new RuntimeException("Number expected but not found");
        }
        return Double.parseDouble(sb.toString());
    }

    private boolean endOfInput() {
        return position >= input.length();
    }

    private char currentChar() {
        return input.charAt(position);
    }

    public static void main(String[] args) {
        String[] testCases = {
            "2 + 3 * (4 + 5)",
            "2 + 3 * 4",
            "(2 + 3) * 4",
            "2 * (3 + 4) * (5 + 6)",
            "1.5 + 2.5 * 3"
        };

        for (String expression : testCases) {
            System.out.println("\nTest Case: " + expression);
            try {
                ExpressionParser parser = new ExpressionParser(expression.replaceAll("\\s+", ""));
                double result = parser.parseExpression();
                System.out.println("Result: " + result);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
