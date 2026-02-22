package com.revision.collection;

import java.util.Optional;

public class NullPointerDemo {

  public static void main(String[] args) {

    Student s1 = new Student(1,"Lokesh",null,8.5);

    Optional<Student> optionalStudent = Optional.ofNullable(s1);

    String city = optionalStudent
      .map(Student::getCity)
      .map(String::toUpperCase)
      .orElse("UNKNOWN");

    System.out.println(city);

    System.out.println("-----------------------------------------------------");

    Student s2 = new Student(1,"Lokesh","Ujjain",8.5);

    Optional<Student> optionalStudent2 = Optional.ofNullable(s2);

    String name = optionalStudent2
      .map(Student::getName)
      .map(String::toUpperCase)
      .orElse("UNKNOWN");

    System.out.println(name);
  }



}
