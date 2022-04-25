package org.ilmostro.basic.bit;

import lombok.extern.slf4j.Slf4j;

/**
 * @author li.bowei
 */
@Slf4j
public class Combo {

	private int storage;

	public static Combo getInstance() {
		return new Combo(0);
	}

	// 分段为5
	private static final int SEGMENT_COUNT = 5;
	// 11111
	private static final int SEGMENT_MASK = ((1 << SEGMENT_COUNT) - 1);
	// 起始的一个字段用于表示状态
	private static final int STATUS_BITS = Integer.SIZE - 2;
	// 30 - 25 这一段表示type
	private static final int TYPE_BITS = STATUS_BITS - SEGMENT_COUNT;
	private static final int TYPE_MASK = (1 << STATUS_BITS) | ((1 << TYPE_BITS) - 1);
	// 25 - 20 这一段表示classify
	private static final int CLASSIFY_BITS = TYPE_BITS - SEGMENT_COUNT;
	private static final int CLASSIFY_MASK = (((1 << (1 + SEGMENT_COUNT)) - 1) << TYPE_BITS) | ((1 << CLASSIFY_BITS) -1);

	public Combo(int storage){
		this.storage = storage;
	}

	public Combo setStatus(int status){
		// 初始化storage的status
		storage <<= 2;
		storage |= status << STATUS_BITS;
		return this;
	}

	public Combo setType(int type){
		// 初始化type
		storage &= TYPE_MASK;
		storage |= type << TYPE_BITS;
		return this;
	}

	public Combo setClassify(int classify){
		// 初始化classify
		storage &= CLASSIFY_MASK;
		storage |= classify << CLASSIFY_BITS;
		return this;
	}

	public int getStatus(){
		return (storage >> STATUS_BITS);
	}
	public int getType(){
		return (storage >> TYPE_BITS) & SEGMENT_MASK;
	}
	public int getClassify(){
		return (storage >> CLASSIFY_BITS) & SEGMENT_MASK;
	}

	public String getShow(){
		return "status:" + getStatus() + ", type:" + getType() + ", classify:" + getClassify();
	}

	@Override
	public String toString() {
		return getShow();
	}

	public static void main(String[] args) {
		Combo combo = Combo.getInstance().setStatus(1)
				.setType(12)
				.setClassify(13);
		log.info("{}", combo.storage);
		log.info(combo.getShow());
		log.info("combo to storage:{}, {}", combo, Integer.toBinaryString(combo.storage));
	}
}
