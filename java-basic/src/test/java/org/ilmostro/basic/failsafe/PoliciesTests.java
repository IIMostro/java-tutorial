package org.ilmostro.basic.failsafe;

import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.util.CharsetUtil;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.bind.DatatypeConverter;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class PoliciesTests {


    @Test
    void test_sample_policy_should_be_success() throws Exception{
        final RetryPolicy<Object> policy = RetryPolicy.builder().handle(Exception.class)
                .withMaxRetries(3)
                .withMaxDuration(Duration.ofSeconds(5))
                .withDelay(Duration.ofSeconds(4)).build();

        final var latch = new CountDownLatch(3);
        Failsafe.with(policy)
                .onSuccess(v1 -> System.out.println("success"))
                .onFailure(v1 -> System.out.println("failure"))
                .onComplete(v1 -> {
                    System.out.println("complete");
                    latch.countDown();
                })
                .run(() -> {
                    System.out.println("run");
                    throw new RuntimeException("test");
                });
        latch.await();
    }

    final PooledByteBufAllocator allocator = new PooledByteBufAllocator(false);
    private ByteBuf buffer = allocator.buffer();

    @BeforeEach
    void before() throws Exception{
        final var value = "40400002010103230b0a071794606754727900000000000042000203040b005b0000000005000003230b0a07170b005b0000000003070103230b0a07170b005b0000000085400103230b0a07170b005b0000000082100003230b0a0717a62323";
        final var bytes = Hex.decodeHex(value.toCharArray());
        buffer.writeBytes(bytes);
    }

    @Test
    void test_decode_policy_should_be_success()  throws Exception{
        final var value = "40400002010103230b0a071794606754727900000000000042000203040b005b0000000005000003230b0a07170b005b0000000003070103230b0a07170b005b0000000085400103230b0a07170b005b0000000082100003230b0a0717a62323";
        final var bytes = Hex.decodeHex(value.toCharArray());

        final var allocator = new PooledByteBufAllocator(false);

        final var buffer = allocator.buffer();
        buffer.writeBytes(bytes);
        System.out.println(ByteBufUtil.prettyHexDump(buffer));

        final var buf2 = buffer.readerIndex(12).readBytes(6);
        System.out.println(ByteBufUtil.prettyHexDump(buf2));

        final var bytes2 = copyBytes(bytes, 12, 6);
        System.out.println(new String(Hex.encodeHex(bytes2)));

        final var bytes1 = new byte[6];
        buf2.readBytes(bytes1);

        final var buf1 = buffer.readerIndex(24);
        System.out.println(ByteBufUtil.prettyHexDump(buf1));
        final var buf = buf1.readByte();
        final var b = buffer.readByte();

        System.out.println("command:" + buf);
        System.out.println("data tag:" + b);
    }

    @Test
    void test_decode_start_should_work(){
//        final var c = buffer.readChar();
        System.out.println(ByteBufUtil.prettyHexDump(buffer));
        System.out.println(buffer.readCharSequence(2, CharsetUtil.US_ASCII));
        System.out.println(buffer.readUnsignedShort());
    }

    @Test
    void test_time_tag_parse_should_work() throws Exception{
        System.out.println(ByteBufUtil.prettyHexDump(buffer));
        final var buf = buffer.readerIndex(6).readBytes(6);
        System.out.println(ByteBufUtil.prettyHexDump(buf));
        final var c = buf.readByte();
        final var s = DatatypeConverter.printHexBinary(new byte[]{c});
        System.out.println(s);
    }

    @Test
    void test_print_char_should_work(){
        final var bytes = new byte[]{00,03,04};
        System.out.println(Objects.toString(bytes[2]));
    }


    public static byte[] copyBytes(byte[] source, int srcPos, int length) {
        byte[] cp = new byte[length];
        System.arraycopy(source, srcPos, cp, 0, length);
        return cp;
    }
}
