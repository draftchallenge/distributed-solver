public class Is {
  public static final String OPERATORS = "*/+-";

  public static boolean expression(String operator, String first, String second) {
    return operator(operator) && number(first) && number(second);
  }

  public static boolean number(char token) {
    return '0' <= token && token <= '9';
  }

  public static boolean number(String token) {
    try {
      Double.parseDouble(token);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  public static boolean openBracket(Object token) {
    return "(".equals(String.valueOf(token));
  }

  public static boolean closeBracket(Object token) {
    return ")".equals(String.valueOf(token));
  }

  public static boolean operator(Object token) {
    return OPERATORS.contains(String.valueOf(token));
  }

  public static byte precedence(Object token) {
    if ("-".equals(String.valueOf(token)) || "+".equals(String.valueOf(token))) {
      return 1;
    }
    return 2;
  }
}

