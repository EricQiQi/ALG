package s4_substring;

/**
 * 76. 最小覆盖子串
 * 和 Hot8-找到字符串中所有字母异位词 使用一样的解题框架
 */
public class Hot12_minWindow {

    public static String minWindow(String s, String t){
        if (s == null || t == null || s.length() < t.length()) return "";

        int[] count = new int[128];
        int remain = t.length();

        for (char ch : t.toCharArray()){
            count[ch]++;
        }

        // 记录最小覆盖字串的起始位置及长度
        int minLen = Integer.MAX_VALUE; // 记录最小覆盖字串的长度，初始值为最大可能的 int 值
        int start = 0; // 记录最小覆盖字串的起始位置

        // 左探子
        int left=0;
        // 右探子疯狂移动
        for(int right=0; right<s.length(); right++){
            char rchar = s.charAt(right);
            // 如果rchar在账单中，则remain--
            if (count[rchar] > 0){
                remain--;
            }
            count[rchar]--;

            // 收缩窗口
            while(remain == 0){
                // 【可变部分】比较长度，记录最小覆盖字串的起始位置
                int len = right-left+1;
                if (len < minLen){
                    minLen = len;
                    start = left;
                }

                // 左探子往右移动
                char lchar = s.charAt(left);
                count[lchar]++;
                if (count[lchar] > 0){
                    remain++;
                }
                left++;
            }
        }

        return minLen==Integer.MAX_VALUE ? "" : s.substring(start, start+minLen);
    }

    public static void main(String[] args) {
        System.out.println(minWindow("ADOBECODEBANC", "ABC"));
    }
}
