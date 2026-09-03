package s8_tree;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 230. 二叉搜索树中第K小的元素
 */
public class Hot230_kthSmallest {

    /**
     * 方法1：迭代-中序遍历
     * 时间复杂度：O(n)，每个节点访问一次
     * 空间复杂度：O(h)，栈的深度，其中 h 是树的高度
     * @param root
     * @param k
     * @return
     */
    public int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;
        while(!stack.isEmpty() || curr != null){
            while(curr != null){
                stack.push(curr);
                curr = curr.left;
            }

            curr = stack.pop();
            k--;
            if(k == 0) break;

            curr = curr.right;
        }
        return curr.val;
    }

    public static void main(String[] args) {
        /**
         *       3
         *      / \
         *     1   4
         *      \
         *       2
         * 中序遍历：1, 2, 3, 4
         */
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(1);
        root.left.right = new TreeNode(2);
        root.right = new TreeNode(4);

        Hot230_kthSmallest solution = new Hot230_kthSmallest();
        System.out.println("第1小: " + solution.kthSmallest(root, 1));  // 1
        System.out.println("第2小: " + solution.kthSmallest(root, 2));  // 2
        System.out.println("第3小: " + solution.kthSmallest(root, 3));  // 3
        System.out.println("第4小: " + solution.kthSmallest(root, 4));  // 4
    }
}
