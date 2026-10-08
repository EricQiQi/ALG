package s14_greedy;

import java.util.ArrayList;
import java.util.List;

/**
 * 763. 划分字母区间
 * 题目：字符串 S 由小写字母组成。我们要把这个字符串划分成尽可能多的片段，同一个字母只会出现在其中的一个片段。返回一个表示每个字符串片段的长度的列表。
 * <p>
 * 示例：
 * 输入：S = "ababcbacadefegdehijhklij"
 * 输出：[9,7,8]
 * 解释：
 * 划分结果为 "ababcbaca", "defegde", "hijhklij"。
 * 每个字母最多只出现一次。
 */
public class Hot763_partitionLabels {

    public List<Integer> partitionLabels(String s) {
        int[] last = new int[26];
        for (int i = 0; i < s.length(); i++) {
            last[s.charAt(i) - 'a'] = i;
        }

        List<Integer> res = new ArrayList<>();
        int start = 0;
        int end = 0;

        for (int i = 0; i < s.length(); i++) {
            end = Math.max(end, last[s.charAt(i) - 'a']);
            if (i == end) {
                res.add(end - start + 1);
                start = i + 1;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Hot763_partitionLabels solution = new Hot763_partitionLabels();
        String s = "ababcbacadefegdehijhklij";
        List<Integer> result = solution.partitionLabels(s);
        for (int r : result) {
            System.out.print(r + " ");
        }
    }
}
