package s1_hash;

import java.util.*;

/**
 * 2.字母异位词
 * 给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。
 * 例如输入: ["eat", "tea", "tan", "ate", "nat", "bat"]
 * 输出:
 * [
 *   ["ate","eat","tea"],
 *   ["nat","tan"],
 *   ["bat"]
 * ]
 */
public class Hot49_groupAnagrams {

    /**
     * 时间复杂度：O(nklogk)
     * 空间复杂度：O(nk)
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for(String s : strs){
            char[] arr = s.toCharArray();
            Arrays.sort(arr);
            String key = new String(arr);
            List<String> list = map.getOrDefault(key, new ArrayList<>());
            list.add(s);
            map.put(key, list);
        }
        return new ArrayList<>(map.values());
    }
    public static void main(String[] args) {
        Hot49_groupAnagrams hot2 = new Hot49_groupAnagrams();
        List<List<String>> lists = hot2.groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"});
        System.out.println(lists);
    }
}
