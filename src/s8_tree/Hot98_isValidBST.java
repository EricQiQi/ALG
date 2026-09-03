package s8_tree;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 98. 验证二叉搜索树
 *
 */
public class Hot98_isValidBST {

    /**
     * 方法1：递归
     * 时间复杂度：O(n)，每个节点访问一次
     * 空间复杂度：O(h)，递归调用栈的深度，其中 h 是树的高度
     * @param root
     * @return
     */
    public static boolean isValidBST_1(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public static boolean isValidBST(TreeNode root, long min, long max) {
        // 终止条件：为空返回true，越界返回false
        if (root == null) return true;
        // 1.自己先过关，才能检查子树
        if(root.val <= min || root.val >= max) return false;

        // 2.检查子树
        return isValidBST(root.left, min, root.val) && isValidBST(root.right, root.val, max);
    }


    /**
     * 方法2：迭代-中序遍历
     * 时间复杂度：O(n)，每个节点访问一次
     * 空间复杂度：O(h)，栈的深度，其中 h 是树的高度
     * @return
     */
    public static boolean isValidBST_2(TreeNode root){
        // 使用栈进行迭代
        Deque<TreeNode> stack = new ArrayDeque<>();
        // 前一个节点的值
        long pre = Long.MIN_VALUE;

        TreeNode curr = root;
        while(!stack.isEmpty() || curr!=null){
            while(curr != null){
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            // 中序遍历，当前节点的值应该大于前一个节点的值
            if (curr.val <= pre){
                return false;
            }
            pre = curr.val;
            curr = curr.right;
        }
        return true;
    }

    public static void main(String[] args) {
        /**
         *       5
         *      / \
         *     4   6
         *        / \
         *       3   7
         */
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(6);
        root.right.left = new TreeNode(3);
        root.right.right = new TreeNode(7);
        System.out.println(new Hot98_isValidBST().isValidBST_1(root));
        System.out.println(new Hot98_isValidBST().isValidBST_2(root));
    }
}
