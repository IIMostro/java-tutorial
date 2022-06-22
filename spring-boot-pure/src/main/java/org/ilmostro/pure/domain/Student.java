package org.ilmostro.pure.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.netty.util.Recycler;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

/**
 * @author li.bowei
 */
@Getter
@ToString
public class Student {

	public static final Recycler<Student> POOL = new Recycler<Student>(10) {
		@Override
		protected Student newObject(Handle<Student> handle) {
			return new Student(handle);
		}
	};

	private String username;
	private Integer age;
	@Getter(AccessLevel.NONE)
	@JsonIgnore
	private final Recycler.Handle<Student> handle;

	public Student(Recycler.Handle<Student> handle) {
		this.handle = handle;
	}

	public void init(String username, Integer age){
		this.username = username;
		this.age = age;
	}

	public void release(){
		handle.recycle(this);
	}
}
