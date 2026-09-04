package s3_slidingWindow;

import java.util.HashMap;
import java.util.Map;

/**
 * 8.无重复字符的最长子串
 */
public class Hot8_lengthOfLongestSubstring {

    /**
     * 滑动窗口—1：hashMap解法
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    public static int lengthOfLongestSubstring_1(String s) {
        Map<Character, Integer> map = new HashMap<>();
        // 记录贪吃蛇的尾部
        int left = 0;
        // 记录最大长度
        int maxLen = 0;

        for (int right = 0; right < s.length(); right++) {
            char ch = s.charAt(right);
            // 先移动左指针
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
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    public static int lengthOfLongestSubstring_2(String s) {
        int[] index = new int[128]; // 对应Ascii码的全部

        int left = 0;
        int maxLen = 0;
        for (int right = 0; right < s.length(); right++) {
            char ch = s.charAt(right);
            // if：如果这个字符之前出现过，并且它上次出现的位置在左指针的【右边】
            if (index[ch] > left) {
                left = index[ch]; // 强行把左指针跳过去，缩紧窗口
            }

            index[ch] = right + 1; // 存入当前下标 + 1 得把原来的 0 覆盖掉,不然计算长度会出错
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring_1("abcabcbb"));
        System.out.println(lengthOfLongestSubstring_2("abcabcbb"));
    }
}
