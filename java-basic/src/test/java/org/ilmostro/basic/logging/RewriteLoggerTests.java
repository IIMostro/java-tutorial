package org.ilmostro.basic.logging;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class RewriteLoggerTests {

    @Test
    void test_rewrite_should_work() throws Exception {
        final var logging = """
                blade.log:java.lang.IllegalArgumentException: Invalid character found in the request target [/gb/v1/ipc/awaken?camera_mac=&clientId=3da51e13-e4d&signature=28846d6285a158ced104e857dcc566c8c0f92e05b17019a9dae776a3d96e8c19&camera_id=xxxS_gb_10845001000000000005_163123456713412345670xc20xa4t_region_date=20240117&type=1&client_id=3da51e13-e4d&token=71232a57b3adda60a2289b9279729e2f&timestamp=1705481039988 ]. The valid characters are defined in RFC 7230 and RFC 3986
                request.log:java.lang.IllegalArgumentException: Invalid character found in the request target [/gb/v1/ipc/awaken?camera_mac=&clientId=3da51e13-e4d&signature=28846d6285a158ced104e857dcc566c8c0f92e05b17019a9dae776a3d96e8c19&camera_id=xxxS_gb_10845001000000000005_163123456713412345670xc20xa4t_region_date=20240117&type=1&client_id=3da51e13-e4d&token=71232a57b3adda60a2289b9279729e2f&timestamp=1705481039988 ]. The valid characters are defined in RFC 7230 and RFC 3986
                """;
        log.debug("{}", logging);
    }
}
