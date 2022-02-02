package jasmine.jragon.piecewise;

import java.util.Map;
import java.util.function.DoubleUnaryOperator;
import java.util.function.DoublePredicate;
import java.util.stream.LongStream;

public final class DoublePiecewiseFunction
        extends PiecewiseFunction<DoublePiecewiseFunction, DoublePredicate, DoubleUnaryOperator>
        implements DoubleUnaryOperator {
    @Override
    public double applyAsDouble(double operand) throws ArithmeticException {
        if (pieceMap.isEmpty())
            return 0;
        for (Map.Entry<DoublePredicate, DoubleUnaryOperator> entry : pieceMap.entrySet()) {
            if (entry.getKey().test(operand))
                return entry.getValue().applyAsDouble(operand);
        }

        throw new ArithmeticException("Input was not covered by any Predicate");
    }

    @Override
    public DoublePiecewiseFunction negate() {
        var output = new DoublePiecewiseFunction();
        for (Map.Entry<DoublePredicate, DoubleUnaryOperator> entry : pieceMap.entrySet())
            output.pieceMap.put(entry.getKey().negate(), entry.getValue());
        return output;
    }

    @Override
    public String testRange(long start, long endInclusive) {
        if (pieceMap.isEmpty())
            return super.testRange(start, endInclusive);

        var keySet = pieceMap.keySet();
        DoublePredicate isOverRepresented = val -> keySet.stream()
                .filter(condition -> condition.test(val))
                .count() > 1;

        DoublePredicate isNotRepresented = val -> keySet.stream()
                .noneMatch(condition -> condition.test(val));

        var overRepresentedNumbers = LongStream.rangeClosed(start, endInclusive)
                .mapToDouble(num -> num)
                .filter(isOverRepresented)
                .boxed()
                .toList();

        var nonRepresentedNumbers = LongStream.rangeClosed(start, endInclusive)
                .mapToDouble(num -> num)
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
