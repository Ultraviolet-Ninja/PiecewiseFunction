package jasmine.jragon;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;

public class PiecewiseFunction implements IntUnaryOperator {
    private final Map<IntPredicate, IntUnaryOperator> pieces;

    public PiecewiseFunction() {
        pieces = new LinkedHashMap<>();
    }

    public void addFunction(IntPredicate p, IntUnaryOperator o) {
        pieces.put(p, o);
    }

    @Override
    public int applyAsInt(int operand) throws ArithmeticException {
        if (pieces.isEmpty())
            return 0;
        for (Map.Entry<IntPredicate, IntUnaryOperator> entry : pieces.entrySet()) {
            if (entry.getKey().test(operand))
                return entry.getValue().applyAsInt(operand);
        }

        throw new ArithmeticException("Input was not covered by any Predicate");
    }
}
