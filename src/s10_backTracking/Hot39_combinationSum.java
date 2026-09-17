package s10_backTracking;

import java.util.ArrayList;
import java.util.List;

/**
 * 39. 组合总和
 * <p>
 * start 索引法回溯 + 减法凑数：把 target 一路减，减到 0 就是一个合法组合，减成负数就剪枝返回。
 * 和 Hot78 子集的唯一区别：递归传的是 i（不是 i+1）—— 因为本题每个数可以重复选，
 * 但传 i 而不是 0 又保证了不会回头选前面的数，避免 [2,2,3] 和 [2,3,2] 这种同组合重复出现。
 * 注意：本题输入无重复元素；若有（Hot40 组合总和II）需排序 + i>start && candidates[i]==candidates[i-1] 跳同层重复，且递归传 i+1。
 */
public class Hot39_combinationSum {

    /** 收集所有和为 target 的组合 */
    private List<List<Integer>> res;
    /** 当前路径（正在凑的组合） */
    private List<Integer> output;

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        // 每次调用重建容器，避免同一对象多次调用时结果累积
        res = new ArrayList<>();
        output = new ArrayList<>();

        backTrack(candidates, target, 0);
        return res;
    }

    /**
     * 回溯：从 start 开始选数凑剩下的 target
     * 不变量：output 里的数下标不递减（都 >= 进入本层时的 start）—— 保证同一个组合只被构造一次
     *
     * @param candidates 候选数（无重复元素，每个可重复使用）
     * @param target     还差多少（凑完为 0）
     * @param start      本层从哪个下标开始选（只能往后或原地，不回头）
     */
    public void backTrack(int[] candidates, int target, int start){
        // 正好凑齐：收获一份组合（必须拷贝，output 后续还会被增删）
        if(target == 0){
            res.add(new ArrayList<>(output));
            return;
        }else if(target < 0){
            // 凑超了：这条路走不通，剪枝返回
            return;
        }

        for(int i=start; i<candidates.length; i++){
            // 做选择：把 candidates[i] 加进当前组合
            output.add(candidates[i]);
            // 递归：target 减掉这个数；传 i 而不是 i+1 —— 允许下一层继续选同一个数（可重复使用）
            backTrack(candidates, target-candidates[i], i);
            // 撤销选择：删掉刚才加的，恢复现场供下一轮 i 尝试
            output.remove(output.size()-1);
        }
    }

    public static void main(String[] args) {
        int[] candidates = {2,3,6,7};
        int target = 7;
        Hot39_combinationSum solution = new Hot39_combinationSum();
        List<List<Integer>> res = solution.combinationSum(candidates, target);
        // 输出 [[2,2,3], [7]]：2+2+3=7、7=7；注意不会出现 [3,2,2]（start 保证下标不递减）
        System.out.println(res);
    }
}
