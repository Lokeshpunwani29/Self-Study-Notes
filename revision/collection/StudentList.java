package com.revision.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StudentList {

  public static void main(String[] args) {
    List<Student> students = new ArrayList<>();


    students.add(new Student(29,"Lokesh","Ujjain",9.6));
    students.add(new Student(54,"Devansh","Indore",8.9));
    students.add(new Student(47,"Rohit","Mumbai",9.89));
    students.add(new Student(12,"Virat","Delhi",6.5));
    students.add(new Student(1,"Tony","New York",9.99));

    System.out.println("--------City Wise------------");
    Collections.sort(students);
    students.forEach(System.out::println);

    System.out.println("---------CGPA WISE-------------");

    Comparator<Student> comp = new CgpaComparator();
    Collections.sort(students, comp);
    students.forEach(System.out::println);

//
//    Comparator<Student> comparator =  Comparator.comparing(Student::getCgpa);
//    students.sort(comparator);
//    students.forEach(System.out::println);

    System.out.println("---------MAP REDUCE COUNT AND SUM-------------");

    double totalCgpa = students.stream()
      .map(Student::getCgpa)
      .reduce(0.0, Double::sum);

    System.out.println("Total CGPA = " + totalCgpa);

    int count = students.stream()
      .map(s -> 1)
      .reduce(0, Integer::sum);

    System.out.println("Student Count = " + count);
    System.out.println("average CGPA = " + totalCgpa/count);


  }
}
