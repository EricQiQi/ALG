package s10_backTracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 46. 全排列
 * <p>
 * 交换法回溯：固定一个位置，把它后面每个候选换上来试一遍，试完换回去（撤销现场）。
 * 和"逐一选择+used数组"的回溯等价，但不需要额外标记数组和 path 列表 —— 直接在 output 原地交换。
 * 注意：swap 法产生的排列不保证字典序（如 {1,2,3} 的输出是 123,132,213,231,321,312），本题不要求顺序所以无所谓。
 */
public class Hot46_permute {

    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();

        // 数组转 List，后续直接在这个 list 上原地交换
        List<Integer> output = new ArrayList<>();
        for(int i : nums){
            output.add(i);
        }

        // 从第 0 个位置开始固定
        backTrack(res, output, nums.length, 0);
        return res;
    }

    /**
     * 回溯：为 [first, len) 区间的每一个位置选一个数固定下来
     * 不变量：[0, first) 是已固定的前缀，[first, len) 是还没用过的候选池
     *
     * @param res    收集所有完整排列
     * @param output 在它上面原地交换（同时充当"当前路径"和"候选池"）
     * @param len    排列总长度
     * @param first  当前要固定的位置下标
     */
    public static void backTrack(List<List<Integer>> res, List<Integer> output, int len, int first){
        // 所有位置都固定完了，收获一份完整排列
        // 必须拷贝！output 后续还会被 swap 改动，直接 add 会让所有结果指向同一个对象
        if(first == len){
            res.add(new ArrayList<>(output));
        }

        // 依次把候选池 [first, len) 里的每个数换到 first 位置上
        for(int i=first; i<len; i++){
            // 做选择：把第 i 个数换到 first 位置固定下来
            Collections.swap(output, first, i);
            // 递归固定下一个位置
            backTrack(res, output, len, first+1);
            // 撤销选择：换回去，恢复候选池原状供下一轮 i 尝试
            Collections.swap(output, first, i);
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        // 输出 6 个排列（3! = 6），注意顺序不是字典序
        System.out.println(permute(nums));
    }
}
