package s15_dp;

import java.util.ArrayList;
import java.util.List;

/**
 * 118. 杨辉三角
 */
public class Hot118_generate {

    /**
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1) 若不记返回值所用空间
     */
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            List<Integer> level = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    level.add(1);
                } else {
                    level.add(res.get(i - 1).get(j - 1) + res.get(i - 1).get(j));
                }
            }
            res.add(level);
        }
        return res;
    }

    public static void main(String[] args) {
        Hot118_generate hot118_generate = new Hot118_generate();
        System.out.println(hot118_generate.generate(5));
    }
}
