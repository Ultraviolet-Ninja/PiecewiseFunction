package jasmine.jragon;

import jasmine.jragon.piecewise.IntPiecewiseFunction;

import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        IntPiecewiseFunction stepFunction = new IntPiecewiseFunction();
        stepFunction.addFunction(num -> num < 0, num -> 0);
        stepFunction.addFunction(num -> num >= 0, num -> 1);

        IntStream.rangeClosed(-10, 10)
                .map(stepFunction)
                .forEach(System.out::println);
    }
}
