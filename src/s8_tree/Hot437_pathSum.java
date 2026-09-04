package s8_tree;

import java.util.HashMap;
import java.util.Map;

/**
 * 437. 路径总和 III
 * 给定一个二叉树，它的每个结点都存放一个整数值。
 * 找出路径和等于给定数值的路径总数。
 * 路径不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。
 * 二叉树不超过1000个节点，且节点数值范围是 [-1000000, 1000000] 的整数。
 */
public class Hot437_pathSum {

    /**
     * 方法1：深度优先遍历，从每个节点开始遍历
     * <p>
     * ！！！！！坑：targetSum是long类型
     * <p>
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(n)
     *
     * @param root
     * @param targetSum
     * @return
     */
    public int pathSum_1(TreeNode root, long targetSum) {
        if (root == null) return 0;

        // 从当前节点开始的路径和
        int ret = rootSum(root, targetSum);

        ret += pathSum_1(root.left, targetSum);
        ret += pathSum_1(root.right, targetSum);
        return ret;
    }

    public int rootSum(TreeNode root, long targetSum) {
        if (root == null) return 0;
        int ret = 0;
        if (root.val == targetSum) ret++;
        ret += rootSum(root.left, targetSum - root.val);
        ret += rootSum(root.right, targetSum - root.val);
        return ret;
    }

    /**
     * 方法2：前缀和
     * <p>
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     *
     * @param root
     * @param targetSum
     * @return
     */
    public int pathSum_2(TreeNode root, long targetSum) {
        // 前缀和映射：key为前缀和，value为该前缀和出现的次数
        Map<Long, Integer> prefixCount = new HashMap<>();
        // 初始前缀和为0，出现一次
        prefixCount.put(0L, 1);
        return dfs(root, 0L, targetSum, prefixCount);
    }

    public int dfs(TreeNode root, long prefixSum, long targetSum, Map<Long, Integer> prefixCount) {
        if (root == null) return 0;

        prefixSum += root.val;
        // 获取前缀和为 prefixSum - targetSum 的路径个数
        int ret = prefixCount.getOrDefault(prefixSum - targetSum, 0);
        // 更新前缀和映射，不能在上一行代码之前，否则会错误地减去当前节点的值
        prefixCount.put(prefixSum, prefixCount.getOrDefault(prefixSum, 0) + 1);

        ret += dfs(root.left, prefixSum, targetSum, prefixCount);
        ret += dfs(root.right, prefixSum, targetSum, prefixCount);

        // 回溯
        prefixCount.put(prefixSum, prefixCount.getOrDefault(prefixSum, 0) - 1);

        return ret;
    }


    public static void main(String[] args) {
        //          10
        //         /  \
        //        5   -3
        //       / \    \
        //      3   2   11
        //     / \   \
        //    3  -2   1
        //
        // targetSum = 8，满足条件的路径：
        //   5 → 3       = 8
        //   5 → 2 → 1   = 8
        //  -3 → 11      = 8
        // 共 3 条

        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(-3);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(2);
        root.right.right = new TreeNode(11);
        root.left.left.left = new TreeNode(3);
        root.left.left.right = new TreeNode(-2);
        root.left.right.right = new TreeNode(1);

        int result_1 = new Hot437_pathSum().pathSum_1(root, 8);
        int result_2 = new Hot437_pathSum().pathSum_2(root, 8);
        System.out.println("路径总数: " + result_1); // 期望输出：3
        System.out.println("路径总数: " + result_2); // 期望输出：3
    }
}
