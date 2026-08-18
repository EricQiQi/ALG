package hash;

import java.util.*;

/**
 * 字母异位词
 */
public class Hot2 {
    public List<List<String>> groupAnagrams(String[] strs) {

        Map<String, List<String>> map = new HashMap<>();
        for(String s : strs){
            char[] arr = s.toCharArray();
            Arrays.sort(arr);
            String key = new String(arr);
            List<String> list = map.getOrDefault(key, new ArrayList<String>());
            list.add(s);
            map.put(key, list);
        }
        return new ArrayList<List<String>>(map.values());

    }
    public static void main(String[] args) {
        Hot2 hot2 = new Hot2();
        List<List<String>> lists = hot2.groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"});
        System.out.println(lists);
    }
}
