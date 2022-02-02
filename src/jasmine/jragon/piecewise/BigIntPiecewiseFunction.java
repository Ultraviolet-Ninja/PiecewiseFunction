package jasmine.jragon.piecewise;

import java.math.BigInteger;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.function.LongFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.LongStream;

public final class BigIntPiecewiseFunction
        extends PiecewiseFunction<BigIntPiecewiseFunction, Predicate<BigInteger>, UnaryOperator<BigInteger>>
        implements UnaryOperator<BigInteger>, LongFunction<BigInteger>, IntFunction<BigInteger> {
    @Override
    public BigIntPiecewiseFunction negate() {
        var output = new BigIntPiecewiseFunction();
        for (Map.Entry<Predicate<BigInteger>, UnaryOperator<BigInteger>> entry : pieceMap.entrySet()) {
            output.pieceMap.put(entry.getKey().negate(), entry.getValue());
        }
        return output;
    }

    @Override
    public String testRange(long start, long endInclusive) {
        if (pieceMap.isEmpty())
            return super.testRange(start, endInclusive);

        var keySet = pieceMap.keySet();
        Predicate<BigInteger> isOverRepresented = val -> keySet.stream()
                .filter(condition -> condition.test(val))
                .count() > 1;

        Predicate<BigInteger> isNotRepresented = val -> keySet.stream()
                .noneMatch(condition -> condition.test(val));

        var overRepresentedNumbers = LongStream.rangeClosed(start, endInclusive)
                .mapToObj(BigInteger::valueOf)
                .filter(isOverRepresented)
                .toList();

        var nonRepresentedNumbers = LongStream.rangeClosed(start, endInclusive)
                .mapToObj(BigInteger::valueOf)
                .filter(isNotRepresented)
                .toList();

        if (nonRepresentedNumbers.isEmpty() && overRepresentedNumbers.isEmpty()) {
            return ALL_VALID_VALUES;
        }

        return OVER_REPRESENTED_NUMBER_STRING.apply(overRepresentedNumbers) + "\n" +
                NON_REPRESENTED_NUMBER_STRING.apply(nonRepresentedNumbers);
    }

    @Override
    public BigInteger apply(BigInteger bigInteger) throws ArithmeticException {
        if (pieceMap.isEmpty())
            return BigInteger.ZERO;
        for (Map.Entry<Predicate<BigInteger>, UnaryOperator<BigInteger>> entry : pieceMap.entrySet()) {
            if (entry.getKey().test(bigInteger))
                return entry.getValue().apply(bigInteger);
        }
        throw new ArithmeticException("Input was not covered by any Predicate");
    }

    @Override
    public BigInteger apply(int value) throws ArithmeticException {
        return apply(BigInteger.valueOf(value));
    }

    @Override
    public BigInteger apply(long value) throws ArithmeticException {
        return apply(BigInteger.valueOf(value));
    }
}
