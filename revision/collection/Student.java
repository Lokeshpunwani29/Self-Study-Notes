package com.revision.collection;

import java.util.Comparator;

public class Student implements Comparable<Student> {

  private int rollNo;
  private String name;
  private String city;
  private double cgpa;

  public Student(int rollNo, String name, String city, double cgpa) {
    this.rollNo = rollNo;
    this.name = name;
    this.city = city;
    this.cgpa = cgpa;
  }

  Student() {}

  public int getRollNo() {
    return rollNo;
  }

  public void setRollNo(int rollNo) {
    this.rollNo = rollNo;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public double getCgpa() {
    return cgpa;
  }

  public void setCgpa(double cgpa) {
    this.cgpa = cgpa;
  }

  @Override
  public int compareTo(Student o) {
    return this.city.compareTo(o.city);
  }

  @Override
  public String toString() {
    return "Student{" +
      "rollNo=" + rollNo +
      ", name='" + name + '\'' +
      ", city='" + city + '\'' +
      ", cgpa=" + cgpa +
      '}';
  }
}
