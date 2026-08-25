package test;


import java.util.*;

public class Hot2 {


    public static int lengthOfLongestSubstring_1(String s) {
        int[] index = new int[128];

        int maxLen = 0;
        int left = 0;
        for (int right = 0; right < s.length(); right++) {
            char ch = s.charAt(right);
            left = Math.max(left, index[ch]);
            index[ch] = right + 1;
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }

    public static int lengthOfLongestSubstring_2(String s) {
        Map<Character, Integer> map = new HashMap<>();

        int maxLen = 0;
        int l = 0;
        for (int r = 0; r<s.length(); r++){
            char ch = s.charAt(r);
            if (map.containsKey(ch)){
                l = Math.max(l, map.get(ch) + 1);
            }
            map.put(ch, r);
            maxLen = Math.max(maxLen, r - l + 1);
        }
        return maxLen;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring_1("abcabcbb"));
        System.out.println(lengthOfLongestSubstring_2("abcabcbb"));
    }
}
