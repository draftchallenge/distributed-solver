import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;

public class Parser {
  public static Stack<String> initStack(String expression) throws ParseException {
    if (expression == null || expression.length() == 0) {
      throw new ParseException("Expression is empty.", 0);
    }
    expression = prepare(expression);
    Stack<String> output = shuntingYard(expression);
    return output;
  }

  private static Stack<String> shuntingYard(String expression) throws ParseException {
    Stack<String> output = new Stack<String>();
    Stack<String> operations = new Stack<String>();
    StringTokenizer stringTokenizer = new StringTokenizer(expression, Is.OPERATORS + "()", true);

    while (stringTokenizer.hasMoreTokens()) {
      String token = stringTokenizer.nextToken();
      if (Is.openBracket(token)) {
        operations.push(token);
        continue;
      }

      if (Is.closeBracket(token)) {
        while (!operations.empty() && !Is.openBracket(operations.lastElement())) {
          output.push(operations.pop());
        }
        operations.pop();
        continue;
      }

      if (Is.number(token)) {
        output.push(token);
        continue;
      }

      if (Is.operator(token)) {
        while (!operations.empty() && Is.operator(operations.lastElement()) && Is.precedence(token) <= Is.precedence(operations.lastElement())) {
          output.push(operations.pop());
        }
        operations.push(token);
        continue;
      }

      throw new ParseException("Unknown token: " + token, 0);
    }

    while (!operations.empty()) {
      output.push(operations.pop());
    }

    Collections.reverse(output);
    return output;
  }

  private static String prepare(String expression) {
    expression = expression.replace(" ", "").replace("(-", "(0-").replace("(+", "(0+");

    while (expression.contains("--")) {
      expression = expression.replace("--", "+");
    }

    if (expression.charAt(0) == '-' || expression.charAt(0) == '+') {
      expression = "0" + expression;
    }

    if (!expression.contains("(") && !expression.contains(")")) {
      return expression;
    }

    StringBuilder stringBuilder = new StringBuilder(expression);
    for (int i = 0; i < stringBuilder.length() - 1; i++) {
      char current = stringBuilder.charAt(i);
      char next = stringBuilder.charAt(i + 1);

      if (Is.closeBracket(current) && Is.number(next)) {
        i++;
        stringBuilder.insert(i, '*');
        continue;
      }

      if (Is.number(current) && Is.openBracket(next)) {
        i++;
        stringBuilder.insert(i, '*');
      }
    }
    return stringBuilder.toString();
  }

  public static Expression[] getExpressions(Stack<String> inputStack) {
    HashMap<String, Expression> expressionMap = new HashMap<String, Expression>();

    String[] workingStack = new String[inputStack.size()];
    workingStack = inputStack.toArray(workingStack);

    for (int i = 0; i < workingStack.length - 2; i++) {
      String operator = workingStack[i];
      String first = workingStack[i + 1];
      String second = workingStack[i + 2];

      if (Is.expression(operator, first, second)) {
        Expression e = new Expression(first, operator, second);
        expressionMap.put(e.getEval(), e);
      }
    }

    Expression[] outputStack = new Expression[expressionMap.size()];
    return expressionMap.values().toArray(outputStack);
  }

  public static Stack<String> rebuildStack(Stack<String> inputStack, Expression[] expressions) {
    Stack<String> outputStack = new Stack<String>();

    String[] workingStack = new String[inputStack.size()];
    workingStack = inputStack.toArray(workingStack);

    HashMap<String, Expression> expressionMap = new HashMap<String, Expression>(expressions.length);
    for (Expression e : expressions) {
      expressionMap.put(e.getEval(), e);
    }

    for (int i = 0; i < workingStack.length; i++) {
      if (i <= workingStack.length - 2) {
        String operator = workingStack[i];
        String first = workingStack[i + 1];
        String second = workingStack[i + 2];

        if (Is.expression(operator, first, second)) {
          Expression current = new Expression(first, operator, second);
          Expression result = expressionMap.get(current.getEval());
          if (result != null) {
            outputStack.push(result.getResult());
            i += 2;
            continue;
          }
        }
      }
      outputStack.push(workingStack[i]);
    }

    return outputStack;
  }

  public static boolean hasResult(Stack<String> inputStack) {
    if (inputStack == null) {
      return false;
    }
    return inputStack.size() <= 1;
  }

  public static String getResult(Stack<String> inputStack) {
    return hasResult(inputStack) ? inputStack.pop() : "";
  }
}
