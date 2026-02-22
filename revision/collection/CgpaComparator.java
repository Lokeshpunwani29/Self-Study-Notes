package com.revision.collection;

import java.util.Comparator;

public class CgpaComparator implements Comparator<Student> {
  @Override
  public int compare(Student o1, Student o2) {
    return Double.compare(o1.getCgpa(), o2.getCgpa());
  }
}
