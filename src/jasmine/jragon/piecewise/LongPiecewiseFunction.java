package jasmine.jragon.piecewise;

import java.util.Map;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.LongPredicate;
import java.util.function.LongUnaryOperator;
import java.util.stream.LongStream;

public final class LongPiecewiseFunction
        extends PiecewiseFunction<LongPiecewiseFunction, LongPredicate, LongUnaryOperator>
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
        var output = new LongPiecewiseFunction();
        for (Map.Entry<LongPredicate, LongUnaryOperator> entry : pieceMap.entrySet())
            output.pieceMap.put(entry.getKey().negate(), entry.getValue());
        return output;
    }

    @Override
    public String testRange(long start, long endInclusive) {
        if (pieceMap.isEmpty())
            return super.testRange(start, endInclusive);

        var keySet = pieceMap.keySet();
        LongPredicate isOverRepresented = val -> keySet.stream()
                .filter(condition -> condition.test(val))
                .count() > 1;

        LongPredicate isNotRepresented = val -> keySet.stream()
                .noneMatch(condition -> condition.test(val));

        var overRepresentedNumbers = LongStream.rangeClosed(start, endInclusive)
                .filter(isOverRepresented)
                .boxed()
                .toList();

        var nonRepresentedNumbers = LongStream.rangeClosed(start, endInclusive)
                .filter(isNotRepresented)
                .boxed()
                .toList();

        if (nonRepresentedNumbers.isEmpty() && overRepresentedNumbers.isEmpty()) {
            return ALL_VALID_VALUES;
        }

        return OVER_REPRESENTED_NUMBER_STRING.apply(overRepresentedNumbers) + "\n" +
                NON_REPRESENTED_NUMBER_STRING.apply(nonRepresentedNumbers);
    }

    public IntPiecewiseFunction toIntPiecewiseFunction() {
        IntPredicate currentPredicate;
        IntUnaryOperator currentOperator;
        var output = new IntPiecewiseFunction();

        for (Map.Entry<LongPredicate, LongUnaryOperator> entry : pieceMap.entrySet()) {
            currentPredicate = num -> entry.getKey().test(num);
            currentOperator = num -> (int) entry.getValue().applyAsLong(num);
            output.addFunction(currentPredicate, currentOperator);
        }

        return output;
    }
}
