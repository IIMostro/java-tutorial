package org.ilmostro.webflux.repository;

import org.ilmostro.webflux.domain.Student;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

/**
 * @author li.bowei
 */
public interface StudentRepository extends R2dbcRepository<Student, Long> {
}
