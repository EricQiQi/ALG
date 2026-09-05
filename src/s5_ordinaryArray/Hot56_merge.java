package s5_ordinaryArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 56.合并区间（Medium）
 *  本题目，数组列数为2，即每个子数组有2个元素
 */
public class Hot56_merge {

    public static int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return intervals;

        // 1.排序.md
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        // 创建List，存储合并后的区间
        List<int[]> merged = new ArrayList<>();

        // 2.遍历区间进行合并
        for (int[] interval : intervals){
            int l = interval[0];
            int r = interval[1];
            // 如果列表为空，或者当前区间的左端点 > 最后一个合并区间的右端点
            // 说明它们不会重叠，直接作为新区间加入列表
            if (merged.isEmpty() || merged.get(merged.size()-1)[1] < l) {
                merged.add(new int[]{l, r});
            }else{
                // 否则，说明有重叠，更新最后一个合并区间的右端点为两者的最大值
                merged.get(merged.size()-1)[1] = Math.max(merged.get(merged.size()-1)[1], r);
            }
        }

        // 3.将 List 转换为二维数组返回，行数为 merged.size()，列数为 2
        return merged.toArray(new int[merged.size()][]);
    }

    public static void main(String[] args) {
        int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        int[][] merged = merge(intervals);
        for (int[] interval : merged) {
            System.out.print("[" + interval[0] + ", " + interval[1] + "] ");
        }
    }
}
