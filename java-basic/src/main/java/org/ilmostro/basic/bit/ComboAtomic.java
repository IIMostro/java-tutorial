package org.ilmostro.basic.bit;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

/**
 * @author li.bowei
 */
@Slf4j
public class ComboAtomic {

	private final AtomicInteger storage;

	public static ComboAtomic getInstance() {
		return new ComboAtomic();
	}

	// 分段为5
	private static final int SEGMENT_COUNT = 5;
	// 11111
	private static final int SEGMENT_MASK = ((1 << SEGMENT_COUNT) - 1);
	// 起始的一个字段用于表示状态
	private static final int STATUS_BITS = Integer.SIZE - 2;
	private static final int STATUS_MASK = (1 << STATUS_BITS) - 1;
	// 30 - 25 这一段表示type
	private static final int TYPE_BITS = STATUS_BITS - SEGMENT_COUNT;
	private static final int TYPE_MASK = (1 << STATUS_BITS) | ((1 << TYPE_BITS) - 1);
	// 25 - 20 这一段表示classify
	private static final int CLASSIFY_BITS = TYPE_BITS - SEGMENT_COUNT;
	// 位移或
	private static final int CLASSIFY_MASK = (((1 << (1 + SEGMENT_COUNT)) - 1) << TYPE_BITS) | ((1 << CLASSIFY_BITS) -1);
	// 位移异或
	private static final int CLASSIFY_MASK_V1 = ((1 << STATUS_BITS) - 1) ^ (((1 << SEGMENT_COUNT) - 1) << CLASSIFY_BITS);

	private ComboAtomic(){
		this(0);
	}

	private ComboAtomic(int storage){
		this.storage = new AtomicInteger(storage);
	}

	public ComboAtomic setStatus(int status){
		// 初始化storage的status
		return getComboAtomic(status, STATUS_MASK, STATUS_BITS);
	}

	public ComboAtomic setType(int type){
		return getComboAtomic(type, TYPE_MASK, TYPE_BITS);
	}

	public ComboAtomic setClassify(int classify){
		return getComboAtomic(classify, CLASSIFY_MASK, CLASSIFY_BITS);
	}

	private ComboAtomic getComboAtomic(int expectedValue, int mask, int bits) {
		if (expectedValue > (1 << SEGMENT_COUNT) - 1) throw new RuntimeException();
		synchronized (storage){
			int value = storage.get();
			int newValue = (value & mask) | (expectedValue << bits);
			storage.compareAndSet(value, newValue);
		}
		return this;
	}

	public int getStatus(){
		return (storage.get() >> STATUS_BITS);
	}
	public int getType(){
		return (storage.get() >> TYPE_BITS) & SEGMENT_MASK;
	}
	public int getClassify(){
		return (storage.get() >> CLASSIFY_BITS) & SEGMENT_MASK;
	}

	public String getShow(){
		return "status:" + getStatus() + ", type:" + getType() + ", classify:" + getClassify();
	}

	@Override
	public String toString() {
		return getShow();
	}

	public static void main(String[] args) {
		ComboAtomic combo = ComboAtomic.getInstance().setStatus(1)
				.setStatus(0)
				.setStatus(1)
				.setType(12)
				.setType(3)
				.setClassify(13)
				.setClassify(10);
		log.info("{}", combo.storage);
		log.info(combo.getShow());
		log.info("combo to storage:{}, {}", combo, Integer.toBinaryString(combo.storage.get()));
		int value = ((1 << STATUS_BITS) - 1) ^ (((1 << SEGMENT_COUNT) - 1) << CLASSIFY_BITS);
		log.info("{}, {}", CLASSIFY_MASK_V1, Integer.toBinaryString(CLASSIFY_MASK_V1));
	}
}
