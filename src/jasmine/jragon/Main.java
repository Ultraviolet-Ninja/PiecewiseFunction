package jasmine.jragon;

import jasmine.jragon.piecewise.IntPiecewiseFunction;

import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        var stepFunction = new IntPiecewiseFunction();
        stepFunction.addFunction(num -> num < 0, num -> 0);
        stepFunction.addFunction(num -> num >= 0, num -> 1);

        System.out.println(stepFunction.testRange(-10, 0));

        IntStream.rangeClosed(-10, 10)
                .map(stepFunction)
                .forEach(System.out::println);

        var antiStepFunction = stepFunction.negate();

//        long start = System.nanoTime();
        System.out.println(antiStepFunction.testRange(-100, 0));
//        long stop = System.nanoTime();
//        System.out.printf("Timer: %,d%n", stop - start);

        IntStream.rangeClosed(-10, 10)
                .map(antiStepFunction)
                .forEach(System.out::println);
    }
}
