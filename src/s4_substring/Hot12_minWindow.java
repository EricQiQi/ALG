package s4_substring;

/**
 * 76. 最小覆盖子串
 * 和 Hot8-找到字符串中所有字母异位词 使用一样的解题框架
 */
public class Hot12_minWindow {

    public static String minWindow(String s, String t) {
        // 1.建立账单
        int[] count = new int[128];
        for (char ch : t.toCharArray()) {
            count[ch]++;
        }
        int remain = t.length();

        // 2.最小长度
        int minLen = Integer.MAX_VALUE;
        // 3.子串的起始位置
        int start = 0;

        int left = 0;
        for (int right = 0; right < s.length(); right++) {
            // 4.右探子移动，销账单
            char rch = s.charAt(right);
            if (count[rch] > 0) {
                remain--;
            }
            count[rch]--;

            while (remain == 0) {
                // 5.达到条件,更新
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    start = left;
                }

                // 6.左探子移动，恢复账单
                char lch = s.charAt(left);
                count[lch]++;
                if (count[lch] > 0) {
                    remain++;
                }
                left++;
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
    }

    public static void main(String[] args) {
        System.out.println(minWindow("ADOBECODEBANC", "ABC"));
    }
}
