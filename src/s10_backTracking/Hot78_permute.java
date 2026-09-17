package s10_backTracking;

import java.util.ArrayList;
import java.util.List;

/**
 * 78. 子集
 * <p>
 * start 索引法回溯：每层从 start 往后选，选过的不再回头，天然保证子集不重复（[1,2] 只会以这个顺序被选出来）。
 * 和全排列的区别：排列要交换法把每个数换到每个位置，子集只需"从 start 往后挑"，且每个节点（不只是叶子）都是合法答案。
 * 注意：本题输入不含重复元素；若含重复（Hot90 子集II）需先排序 + i>start && nums[i]==nums[i-1] 跳过去重。
 */
public class Hot78_permute {

    /** 收集所有子集（每个节点到达时都收割一次） */
    private List<List<Integer>> res = new ArrayList<>();
    /** 当前路径（当前正在构造的子集），递归前后同步增删（回溯） */
    private List<Integer> output = new ArrayList<>();

    public List<List<Integer>> permute(int[] nums) {
        // 题目标准方法名为 subsets，从下标 0 开始选
        backTrack(nums, 0);
        return res;
    }

    /**
     * 回溯：从 start 开始逐个尝试"把 nums[i] 加进当前子集"
     * 不变量：output 里都是下标递增的元素，且都 < start —— 保证子集不重复
     *
     * @param nums  原数组
     * @param start 本层能从哪个下标开始选（只能往后选，不回头）
     */
    public void backTrack(int[] nums, int start){
        // 进入每个节点就收割：子集问题是"每个节点都是答案"，不需要像排列那样等钻满（first == len）才收
        // 必须拷贝！output 后续还会被增删，直接 add 会让所有结果指向同一个对象
        res.add(new ArrayList<>(output));

        for(int i=start; i<nums.length; i++){
            // 做选择：把 nums[i] 加进当前子集
            output.add(nums[i]);
            // 递归：只能选 i 之后的元素（i+1，不包含 i），不能回头选前面的 —— 避免出现 [2,1] 这种重复
            backTrack(nums, i+1);
            // 撤销选择：把刚才加的删掉，恢复现场供下一轮 i 尝试
            output.remove(output.size()-1);
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        Hot78_permute hot78_permute = new Hot78_permute();
        // 输出 8 个子集（2^3 = 8）：[], [1], [1,2], [1,2,3], [1,3], [2], [2,3], [3]
        System.out.println(hot78_permute.permute(nums));
    }
}
