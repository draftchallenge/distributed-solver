public class Expression {
  private String operator;
  private String first;
  private String second;

  private String result;

  public Expression(String first, String operator, String second) {
    this.operator = operator;
    this.first = first;
    this.second = second;
  }

  public String getResult() {
    if (result == null) {
      throw new IllegalStateException("Result does not exist yet.");
    }
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getEval() {
    return first + operator + second;
  }

  public String getOperator() {
    return operator;
  }

  public boolean hasResult() {
    return result != null;
  }

  String getSecond() {
    return second;
  }

  String getFirst() {
    return first;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Expression that = (Expression) o;

    if (!first.equals(that.first)) return false;
    if (!operator.equals(that.operator)) return false;
    if (!second.equals(that.second)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = operator.hashCode();
    result = 31 * result + first.hashCode();
    result = 31 * result + second.hashCode();
    return result;
  }
}
