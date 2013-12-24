import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Stack;

public class Tests {

  public static void main(String[] args) throws ParseException {

    String[] testSuccess = new String[]{
      "2+7*3(33+44)4",
      "7+4+2",
      "(7+3)",
      "123",
      "123+234",
      "(23+22)",
      "2+3+4",
      "+3/4"
    };

    String[] testFail = new String[]{
      "e23",
      "23e",
      "23e23"
    };


    for (String s : testSuccess) {
      run(s);
    }
  }

  private static void run(String s) throws ParseException {
    System.out.print(s + " = ");

    Stack<String> stack = Parser.initStack(s);

    do {
      Expression[] expressions = Parser.getExpressions(stack);
      for (Expression e : expressions) {
        String resultFromAgent = dummySolver(e);
        e.setResult(resultFromAgent);
      }

      stack = Parser.rebuildStack(stack, expressions);
    } while (!Parser.hasResult(stack));

    System.out.println(Parser.getResult(stack));
  }

  public static String dummySolver(Expression e) {
    BigDecimal numberOne = new BigDecimal(e.getFirst());
    BigDecimal numberTwo = new BigDecimal(e.getSecond());

    String operator = e.getOperator();
    if ("+".equals(operator)) {
      return numberTwo.add(numberOne).toString();
    }
    if ("-".equals(operator)) {
      return numberTwo.subtract(numberOne).toString();
    }
    if ("/".equals(operator)) {
      return numberTwo.divide(numberOne).toString();
    }
    if ("*".equals(operator)) {
      return numberTwo.multiply(numberOne).toString();
    }
    return "";
  }

}
