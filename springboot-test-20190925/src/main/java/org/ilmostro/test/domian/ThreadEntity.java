package org.ilmostro.test.domian;

import lombok.Builder;
import lombok.Data;

/**
 * @author li.bowei on 2019-10-21.
 * @description
 */
@Data
@Builder
public class ThreadEntity {

    private Integer id;
    private String name;
    private Long threadId;
    private String threadName;
    private String session;
}
