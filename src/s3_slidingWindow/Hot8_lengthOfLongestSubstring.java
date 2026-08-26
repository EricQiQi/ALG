package s3_slidingWindow;

import java.util.HashMap;
import java.util.Map;

/**
 * 无重复字符的最长子串
 */
public class Hot8_lengthOfLongestSubstring {

    /**
     * 滑动窗口—1：hashMap解法
     *
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring_1(String s) {
        Map<Character, Integer> map = new HashMap<>();
        // 记录贪吃蛇的尾部
        int left = 0;
        // 记录最大长度
        int maxLen = 0;

        for (int right = 0; right < s.length(); right++) {
            char ch = s.charAt(right);
            if (map.containsKey(ch)) {
                left = Math.max(left, map.get(ch) + 1);
            }
            map.put(ch, right);
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }

    /**
     * 滑动窗口—2：数组解法
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring_2(String s) {
        int[] index = new int[128]; // 对应Ascii码的全部

        int left = 0;
        int maxLen = 0;
        for (int right = 0; right < s.length(); right++) {
            char ch = s.charAt(right);
            // 先更新贪吃蛇的尾部
            left = Math.max(left, index[ch]);
            // 记录当前位置
            index[ch] = right + 1;
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring_1("abcabcbb"));
        System.out.println(lengthOfLongestSubstring_2("abcabcbb"));
    }
}
