package jasmine.jragon.piecewise;

import java.util.LinkedHashMap;
import java.util.Map;

abstract class PiecewiseFunction<F extends PiecewiseFunction<F, P, O>, P, O> {
    protected final Map<P, O> pieceMap;

    public PiecewiseFunction() {
        pieceMap = new LinkedHashMap<>();
    }

    public void addFunction(P p, O o) {
        pieceMap.put(p, o);
    }

    public void addFunction(F f) {
        pieceMap.putAll(f.pieceMap);
    }

    public abstract F negate();
}
