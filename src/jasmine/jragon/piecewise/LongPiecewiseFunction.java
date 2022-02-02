package jasmine.jragon.piecewise;

import java.util.Map;
import java.util.function.LongPredicate;
import java.util.function.LongUnaryOperator;

public class LongPiecewiseFunction extends PiecewiseFunction<LongPiecewiseFunction, LongPredicate, LongUnaryOperator>
        implements LongUnaryOperator {
    @Override
    public long applyAsLong(long operand) throws ArithmeticException {
        if (pieceMap.isEmpty())
            return 0;
        for (Map.Entry<LongPredicate, LongUnaryOperator> entry : pieceMap.entrySet()) {
            if (entry.getKey().test(operand))
                return entry.getValue().applyAsLong(operand);
        }

        throw new ArithmeticException("Input was not covered by any Predicate");
    }

    @Override
    public LongPiecewiseFunction negate() {
        LongPiecewiseFunction output = new LongPiecewiseFunction();
        for (Map.Entry<LongPredicate, LongUnaryOperator> entry : pieceMap.entrySet())
            output.pieceMap.put(entry.getKey().negate(), entry.getValue());
        return output;
    }
}
