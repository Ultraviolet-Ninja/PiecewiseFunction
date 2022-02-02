package jasmine.jragon.piecewise;

import java.util.Map;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;

public class IntPiecewiseFunction extends PiecewiseFunction<IntPiecewiseFunction, IntPredicate, IntUnaryOperator>
        implements IntUnaryOperator {
    @Override
    public int applyAsInt(int operand) throws ArithmeticException {
        if (pieceMap.isEmpty())
            return 0;
        for (Map.Entry<IntPredicate, IntUnaryOperator> entry : pieceMap.entrySet()) {
            if (entry.getKey().test(operand))
                return entry.getValue().applyAsInt(operand);
        }

        throw new ArithmeticException("Input was not covered by any Predicate");
    }
}
