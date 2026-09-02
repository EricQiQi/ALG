package s8_tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 108. 将有序数组转换为二叉搜索树
 * 给你一个整数数组 nums ，其中元素已经按 升序 排列，请你将其转换为一棵 高度平衡 的二叉搜索树。
 * 高度平衡 二叉树是一棵满足「每个节点的左右子树的高度差的绝对值不超过 1 」的二叉树。
 */
public class Hot108_sortedArrayToBST {
    public TreeNode sortedArrayToBST(int[] nums) {
        return build(nums, 0, nums.length - 1);
    }

    public TreeNode build(int[] nums, int left, int right){
        if(left > right) return null;
        // 取中间值作为根节点
        int mid = left + (right - left)/2;

        TreeNode root = new TreeNode(nums[mid]);

        root.left = build(nums, left, mid-1);
        root.right = build(nums, mid+1, right);
        return root;
    }
    
    public static void main(String[] args) {
        // 输入：[-10, -3, 0, 5, 9]
        // 输出：[0, -10, 5, -3, 9]
        //
        //        0
        //       / \
        //     -10   5
        //     /   /
        //   -3  9
        int[] nums = {-10, -3, 0, 5, 9};
    
        Hot108_sortedArrayToBST solution = new Hot108_sortedArrayToBST();
        TreeNode root = solution.sortedArrayToBST(nums);
    
        // 用层序遍历验证结果
        preOrder(root);
    }

    public static void preOrder(TreeNode root){
        if(root == null) return;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()){
            TreeNode node = stack.pop();
            System.out.print(node.val + " ");
            if(node.right != null) stack.push(node.right);
            if(node.left != null) stack.push(node.left);
        }
    }
}
