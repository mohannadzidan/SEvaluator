package sevaluator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sevaluator.exceptions.SyntaxErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class SEvaluatorTesting {
    private static Evaluator evaluator;

    @BeforeAll
    static void setup() {

        evaluator = new Evaluator(Evaluator.Initialization.ALL);

        double x = evaluator.evaluate("65.2 / 3 + 5 * 3 + (1 + 1 / sin(0.5 * 3.14))");

        evaluator.addOperation(new Operation() {

            @Override
            public double apply(Evaluable lhs, Evaluable rhs) {
                return lhs.evaluate() % rhs.evaluate();
            }

            @Override
            public char getOperator() {
                return '%';
            }

            @Override
            public int getPriority() {
                return '2'; // as same as division
            }
        });

        evaluator.addFunction(new Function() {
            @Override
            public double apply(Evaluable value) {
                return Math.log(value.evaluate());
            }

            @Override
            public String getKeyword() {
                return "log";
            }
        });

    }

    @Test
    void testSimpleExpressionPacking() {
        String infix = "3*3";
        int expectedIndex = infix.length();
        String expectedStructure = "(3.0*3.0)";
        Expression expression = new Expression();
        int index = Expression.pack(expression, evaluator, infix, 0, infix.length() - 1);
        assertEquals(expectedStructure, expression.toString());
        assertEquals(expectedIndex, index);
    }

    @Test
    void testNestedExpressionPacking() {
        String infix = "(25+2.25/2)+60*5";
        int expectedIndex = infix.length();
        String expectedStructure = "((25.0+2.25/2.0)+60.0*5.0)";
        Expression expression = new Expression();
        int index = Expression.pack(expression, evaluator, infix, 0, infix.length() - 1);
        assertEquals(expectedStructure, expression.toString());
        assertEquals(expectedIndex, index);

    }

    @Test
    void testFunctionPacking() {
        String infix = "1+sin(3.14159265/2)";
        int expectedIndex = infix.length();
        String expectedStructure = "(1.0+sin(3.14159265/2.0))";
        Expression expression = new Expression();
        int index = Expression.pack(expression, evaluator, infix, 0, infix.length() - 1);
        assertEquals(expectedStructure, expression.toString());
        assertEquals(expectedIndex, index);
    }

    @Test
    void testEmpty() {
        assertEquals(0, evaluator.evaluate(""));
        assertEquals(0, evaluator.evaluate(null));
    }

    @Test
    void testSingleTerm() {
        assertEquals(22.536, evaluator.evaluate("22.536"));
    }

    @Test
    void testAdditionSimple() {
        assertEquals(28, evaluator.evaluate("3.50+ 24.5"));

    }

    @Test
    void testAdditionMany() {
        assertEquals(1375, evaluator.evaluate("3.10+ 24.5+ 982.25+ 365.15"));
    }

    @Test
    void testSubtraction() {
        assertEquals(41.5, evaluator.evaluate("60.3 - 3.5 - 15.3"));
    }

    @Test
    void testSubtractionAndAddition() {
        assertEquals(-292.5, evaluator.evaluate("300.10+ 24.5- 982.25+ 365.15 "));
    }

    @Test
    void testMultiplication() {
        assertEquals(4.5, evaluator.evaluate("3*3*0.5"));
    }

    @Test
    void testMultiplicationMixed() {
        assertEquals(88, evaluator.evaluate("33*3 +1 + 5 - 6*3+1"));
        assertEquals(742.75, evaluator.evaluate("24.5 + 982.25 - 365 + 10*10.10 "));
    }

    @Test
    void testFunctionEvaluation() {
        assertEquals(Math.sin(Math.cos(Math.PI)), evaluator.evaluate("sin(cos(" + Math.PI + "))"));
        assertEquals(Math.sin(3.14) + 5, evaluator.evaluate("sin(3.14)+5"));

    }

    @Test
    void testAll() {
        assertEquals(1 / Math.sin(Math.cos(Math.PI) * 2 + 5) * Math.pow(3, 10), evaluator.evaluate("1/sin(cos(" + Math.PI + ") * 2+5)*3^10"));
        assertEquals(Math.log(5), evaluator.evaluate("log(5)"));

    }

    @Test
    void testExceptions() {
        assertThrows(SyntaxErrorException.class, () -> evaluator.evaluate("55+-9"));
        assertThrows(SyntaxErrorException.class, () -> evaluator.evaluate("(5663)+50("));
        assertThrows(SyntaxErrorException.class, () -> evaluator.evaluate("30+50)"));
        assertThrows(SyntaxErrorException.class, () -> evaluator.evaluate("+9+"));
        assertThrows(SyntaxErrorException.class, () -> evaluator.evaluate("50+nonFunc(56)"));
    }

}