package org.ilmostro.basic.leetcode.pigeonhole;

/**
 *
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 *
 * 如果不存在公共前缀，返回空字符串 ""。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：strs = ["flower","flow","flight"]
 * 输出："fl"
 * 示例 2：
 *
 * 输入：strs = ["dog","racecar","car"]
 * 输出：""
 * 解释：输入不存在公共前缀。
 *  
 *
 * 提示：
 *
 * 1 <= strs.length <= 200
 * 0 <= strs[i].length <= 200
 * strs[i] 仅由小写英文字母组成
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-common-prefix
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @link {https://leetcode-cn.com/problems/longest-common-prefix/}
 * @author li.bowei
 */
public class Solution3 {

    public static void main(String[] args) {
        String[] strings = {"flower","flow","flight"};
        String s = new Solution3().longestCommonPrefix(strings);
        System.out.println(s);
    }

    public String longestCommonPrefix(String[] strs) {

        if(strs == null || strs.length == 0){
            return "";
        }

        String prefix = strs[0];
        int index = prefix.length();
        for (int i = 1; i < strs.length; i++) {
            int min = Math.min(prefix.length(), strs[i].length());
            char[] prefixChar = prefix.toCharArray();
            char[] currentChar = strs[i].toCharArray();
            int currentIndex = 0;
            for (int j = 0; j < min; j++) {
                if(prefixChar[j] == currentChar[j]){
                    currentIndex++;
                }else{
                    break;
                }
            }
            if(currentIndex == 0){
                return "";
            }
            if(currentIndex < index){
                index = currentIndex;
            }
        }
        return prefix.substring(0, index);
    }
}
