package jasmine.jragon.piecewise;

import java.util.Map;
import java.util.function.DoubleUnaryOperator;
import java.util.function.DoublePredicate;

public class DoublePiecewiseFunction 
        extends PiecewiseFunction<DoublePiecewiseFunction, DoublePredicate, DoubleUnaryOperator>
        implements DoubleUnaryOperator {
    @Override
    public double applyAsDouble(double operand) {
        if (pieceMap.isEmpty())
            return 0;
        for (Map.Entry<DoublePredicate, DoubleUnaryOperator> entry : pieceMap.entrySet()) {
            if (entry.getKey().test(operand))
                return entry.getValue().applyAsDouble(operand);
        }

        throw new ArithmeticException("Input was not covered by any Predicate");
    }
}
