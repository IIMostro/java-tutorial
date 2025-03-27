package org.ilmostro.basic.version;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author hxq8176
 */
public class UpdateUtil {

//	private static final Logger log = LoggerFactory.getLogger(UpdateUtil.class);

    public static int compareVerNum(String version1, String version2) {
        if (version1 == null || version2 == null) {
            return -1;
        }
        if (StringUtils.equals(version1.trim(), version2.trim())) {
            return 0;
        }
        String[] s1 = version1.trim().split("\\.");
        String[] s2 = version2.trim().split("\\.");
        int n1 = s1.length;
        int n2 = s2.length;
        if (n1 - n2 > 0) {
            for (int i = 0; i < n1 - n2; i++) {
                s2 = (String[]) ArrayUtils.add(s2, "0");
            }
        } else if (n2 - n1 > 0) {
            for (int i = 0; i < n2 - n1; i++) {
                s1 = (String[]) ArrayUtils.add(s1, "0");
            }
        }

        for (int i = 0; i < (n1 > n2 ? n1 : n2); i++) {
            if (Integer.parseInt(s1[i]) - Integer.parseInt(s2[i]) > 0) {
                return 2;
            }
            if (Integer.parseInt(s1[i]) - Integer.parseInt(s2[i]) < 0) {
                return 1;
            }
        }
        return 0;
    }
}
