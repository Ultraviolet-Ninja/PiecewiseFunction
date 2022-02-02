package jasmine.jragon.piecewise;

import java.util.Map;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public final class IntPiecewiseFunction
        extends PiecewiseFunction<IntPiecewiseFunction, IntPredicate, IntUnaryOperator>
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

    @Override
    public IntPiecewiseFunction negate() {
        var output = new IntPiecewiseFunction();
        for (Map.Entry<IntPredicate, IntUnaryOperator> entry : pieceMap.entrySet())
            output.pieceMap.put(entry.getKey().negate(), entry.getValue());
        return output;
    }

    @Override
    public String testRange(long start, long endInclusive) {
        if (pieceMap.isEmpty())
            return super.testRange(start, endInclusive);

        if (start > Integer.MAX_VALUE || start < Integer.MIN_VALUE ||
                endInclusive > Integer.MAX_VALUE || endInclusive < Integer.MIN_VALUE) {
            throw new IllegalArgumentException(
                    "Start or end value is either too large or too small to be represented as an int"
            );
        }

        var keySet = pieceMap.keySet();
        IntPredicate isOverRepresented = val -> keySet.stream()
                .filter(condition -> condition.test(val))
                .count() > 1;

        IntPredicate isNotRepresented = val -> keySet.stream()
                .noneMatch(condition -> condition.test(val));

        var overRepresentedNumbers = IntStream.rangeClosed((int) start, (int) endInclusive)
                .filter(isOverRepresented)
                .boxed()
                .toList();

        var nonRepresentedNumbers = IntStream.rangeClosed((int) start, (int) endInclusive)
                .filter(isNotRepresented)
                .boxed()
                .toList();

        if (nonRepresentedNumbers.isEmpty() && overRepresentedNumbers.isEmpty()) {
            return ALL_VALID_VALUES;
        }

        return OVER_REPRESENTED_NUMBER_STRING.apply(overRepresentedNumbers) + "\n" +
                NON_REPRESENTED_NUMBER_STRING.apply(nonRepresentedNumbers);
    }
}
