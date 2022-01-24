package jasmine.jragon;

import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        PiecewiseFunction stepFunction = new PiecewiseFunction();
        stepFunction.addFunction(num -> num < 0, num -> 0);
        stepFunction.addFunction(num -> num >= 0, num -> 1);

        IntStream.rangeClosed(-10, 10)
                .map(stepFunction)
                .forEach(System.out::println);
    }
}
