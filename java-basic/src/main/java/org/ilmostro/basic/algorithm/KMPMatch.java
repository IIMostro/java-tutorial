package org.ilmostro.basic.algorithm;

/**
 * @author li.bowei
 */
public class KMPMatch {


    public static void main(String[] args) {
        String source = "abcdeabdabcdf";
        String target = "abcdf";
        match(source, target);
    }

    /**
     * KMP朴素模式匹配算法
     *
     * @param source 原字符串
     * @param target 需要匹配的字符串
     * @return 是否匹配成功
     */
    public static boolean match(String source, String target) {
        char[] charsS = source.toCharArray();
        char[] charsT = target.toCharArray();
        int sizeI = charsS.length - charsT.length;
        int sizeJ = charsT.length;
        for (int i = 0; i <= sizeI; i ++) {
            for (int j = 0; j < sizeJ; j ++) {
                if (charsS[i + j] != charsT[j]) {
                    break;
                }
                if (sizeJ - 1 == j) {
                    return true;
                }
            }
        }
        return false;
    }
}
