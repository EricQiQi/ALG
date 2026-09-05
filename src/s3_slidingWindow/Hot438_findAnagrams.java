package s3_slidingWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 438. 找到字符串中所有字母异位词
 */
public class Hot438_findAnagrams {

    /**
     * 推荐使用！！！！！！
     * 全能轻量级数组框架
     * @param s
     * @param p
     * @return
     */
    public static List<Integer> findAnagrams_2(String s, String p) {
        if (s == null || p == null || s.length() < p.length()) return new ArrayList<>();

        // 1-建立账单：count记录需要的目标字符数量
        int[] count = new int[128];
        for (char c : p.toCharArray()) {
            count[c]++;
        }
        // 2-剩余要寻找的字符数量
        int remain = p.length();

        // 记录结果
        List<Integer> res = new ArrayList<>();

        // 3-双指针
        int left = 0;
        for (int right = 0; right < s.length(); right++) {
            // 4-右探子移动,找目标字符,减少账单
            char rchar = s.charAt(right);
            // 账单中存在目标字符，则剩余要寻找的字符数量-1
            if (count[rchar] > 0) {
                remain--;
            }
            // 账单更新
            count[rchar]--;

            // 5.判断窗口是否要收缩
            while (remain == 0) {
                // 6.达到目标条件,记录结果
                if (right - left + 1 == p.length()) {
                    res.add(left);
                }

                // 7.恢复账单,左探子右移:把找到的字母异位词又遍历一遍,目的就是恢复账单和remain
                char lchar = s.charAt(left);
                count[lchar]++;
                if (count[lchar] > 0) {
                    remain++;
                }
                left++;
            }
        }
        return res;
    }

    /**
     * 双Map，一个need，一个window，判断window是否满足need
     * @param s
     * @param p
     * @return
     */
    public static List<Integer> findAnagrams_1(String s, String p) {
        // 1. 账本初始化：need 记录需要的字符及数量，window 记录当前窗口内的字符及数量
        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();

        // 统计目标字符串 t 中每个字符的需求量
        for (char c : p.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        // 2. 左右探子初始化
        int left = 0, right = 0;
        int valid = 0; // 记录窗口中已经满足 need 条件的字符种类数
        int start = 0; // 记录最小覆盖子串的起始位置
        List<Integer> res = new ArrayList<>(); // 记录结果

        // 3. 右探子疯狂往前冲
        while (right < s.length()) {
            char ch = s.charAt(right);
            right++;

            if (need.containsKey(ch)) {
                window.put(ch, window.getOrDefault(ch, 0) + 1);
                // 要用equals判断
                if (window.get(ch).equals(need.get(ch))) {
                    valid++;
                }
            }

            // 4. 判断左侧窗口是否要收缩（将 windowNeedsShrink 替换为具体的判断条件）
            // 比如在“最小覆盖子串”中，条件通常是 valid == need.size()
            while (right - left >= p.length()) {
                if (valid == need.size()) res.add(left);

                char d = s.charAt(left); // 要移除的字符
                left++;
                if (need.containsKey(d)) {
                    if (need.get(d).equals(window.get(d))) {
                        valid--;
                    }
                    window.put(d, window.get(d) - 1);
                }
            }
        }

        return res;
    }

    public static void main(String[] args) {
        System.out.println(findAnagrams_1("cbaebabacd", "abc"));
        System.out.println(findAnagrams_2("cbaebabacd", "abc"));
        System.out.println("---");
        System.out.println(findAnagrams_1("abab", "ab"));
        System.out.println(findAnagrams_2("abab", "ab"));
        System.out.println("---");
        System.out.println(findAnagrams_1("aaa", "a"));
        System.out.println(findAnagrams_2("aaa", "a"));
        System.out.println("---");
        System.out.println(findAnagrams_1("a", "ab"));
        System.out.println(findAnagrams_2("a", "ab"));
    }
}
