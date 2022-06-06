package org.ilmostro.basic;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * @author li.bowei
 */
@Slf4j
@Data
@ToString
public class Student implements Comparable<Student>{

	private int t1;
	private int t2;
	private int t3;

	@Override
	public int compareTo(Student o) {
		if (o.t1 > t1) return 1;
		if (o.t2 > t2) return 1;
		if (o.t3 > t3) return 1;
		return 0;
	}


	public static void main(String[] args) {
		final Student s1 = new Student();
		s1.t1 = 1;
		s1.t2 = 1;
		s1.t3 = 2;
		final Student s2 = new Student();
		s2.t1 = 1;
		s2.t2 = 2;
		s2.t3 = 1;
		final Student s3 = new Student();
		s3.t1 = 1;
		s3.t2 = 2;
		s3.t3 = 2;
		final Student s4 = new Student();
		s4.t1 = 2;
		s4.t2 = 1;
		s4.t3 = 2;

		List<Student> students = Arrays.asList(s1, s2, s3, s4);
//		Collections.sort(students);
		students = students.stream().sorted().collect(Collectors.toList());
//		students.sort(Student::compareTo);
		for (Student student : students) {
			log.error("{}", student);
		}
	}
}
