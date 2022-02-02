package jasmine.jragon.piecewise;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

abstract sealed class PiecewiseFunction<F extends PiecewiseFunction<F, P, O>, P, O>
        permits IntPiecewiseFunction, DoublePiecewiseFunction, LongPiecewiseFunction, BigIntPiecewiseFunction {

    protected static final String ALL_VALID_VALUES = "All values tested are included in this piecewise function";

    protected static final Function<List<? extends Number>, String> OVER_REPRESENTED_NUMBER_STRING = list ->
            "The given range has " + (list.isEmpty() ?
                    "no number that is represented more than once" :
                    String.format("the following number(s) that occur more than once: %s", list));

    protected static final Function<List<? extends Number>, String> NON_REPRESENTED_NUMBER_STRING = list ->
            "The given range has " + (list.isEmpty() ?
                    "no number that isn't represented" :
                    String.format("the following number(s) that aren't represented: %s", list));

    protected final Map<P, O> pieceMap;

    public PiecewiseFunction() {
        pieceMap = new LinkedHashMap<>();
    }

    public void addFunction(P p, O o) {
        Objects.requireNonNull(p);
        Objects.requireNonNull(o);
        pieceMap.put(p, o);
    }

    public void appendFunctions(F f) {
        Objects.requireNonNull(f);
        pieceMap.putAll(f.pieceMap);
    }

    public abstract F negate();

    public String testRange(long start, long endInclusive) {
        return String.format("All values in the range of %d - %d are not represented", start, endInclusive);
    }
}
