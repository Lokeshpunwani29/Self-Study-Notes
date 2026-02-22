package com.revision.collection;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class PredicateConsumerSupplierDemo {

  public static void main(String[] args) {


    Supplier<Integer> supplier = () -> new Random().nextInt(50);
    Integer value = supplier.get();
    System.out.println("Supplied Value: " + value);

    Predicate<Integer> isGreaterThan20 = n -> n > 20;
    boolean result = isGreaterThan20.test(value);
    System.out.println("Is value > 20 ? " + result);


    Consumer<Integer> printValue = n ->
      System.out.println("Consumed Value: " + n);

    if (result) {
      printValue.accept(value);
    } else {
      System.out.println("Value is not greater than 20");
    }
  }
}

