package org.ilmostro.pure.statemachine;

import lombok.Data;
import lombok.ToString;

/**
 * @author li.bowei
 */
@Data
@ToString
public class OrderEntity {

	private Long orderId;
	private OrderStatus status;
}
