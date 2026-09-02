package s8_tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * 94. 二叉树的中序遍历
 */
public class Hot94_inorderTraversal {

    /**
     * 二叉树前序遍历：根 左 右
     *
     * @param root
     * @param res
     */
    public void preOrder(TreeNode root, List<Integer> res) {
        if (root == null) return;

        res.add(root.val);
        preOrder(root.left, res);
        preOrder(root.right, res);
    }

    /**
     * 二叉树中序遍历：左 根 右
     *
     * @param root
     * @param res
     */
    public void midOrder(TreeNode root, List<Integer> res) {
        if (root == null) return;

        midOrder(root.left, res);
        res.add(root.val);
        midOrder(root.right, res);
    }

    /**
     * 二叉树后序遍历：左 右 根
     *
     * @param root
     * @param res
     */
    public void postOrder(TreeNode root, List<Integer> res) {
        if (root == null) return;

        postOrder(root.left, res);
        postOrder(root.right, res);
        res.add(root.val);
    }

    /**
     * 前序遍历：根 左 右
     * 入栈顺序：根 右 左
     * 递归版本
     *
     * @param root
     * @param res
     */
    public void preOrder_1(TreeNode root, List<Integer> res) {
        if (root == null) return;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            res.add(node.val);
            // 先把右节点压入栈中，入栈顺序和出栈顺序相反
            if (node.right != null) stack.push(node.right);
            if (node.left != null) stack.push(node.left);
        }
    }

    /**
     * 中序遍历：左 根 右
     * 入栈顺序：左 右
     * 递归版本
     *
     * @param root
     * @param res
     */
    public void midOrder_1(TreeNode root, List<Integer> res) {
        if (root == null) return;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            // 弹出左节点，访问它，再访问右节点
            curr = stack.pop();
            res.add(curr.val);
            curr = curr.right;
        }
    }

    /**
     * 后序遍历：左 右 根
     * 入栈顺序：根 左 右
     * 出栈顺序：根 右 左
     * 反转结果：左 右 根
     *
     * 递归版本
     *
     * @param root
     * @param res
     */
    public void postOrder_1(TreeNode root, List<Integer> res) {
        if (root == null) return;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            res.add(node.val);
            // 先把左节点压入栈中，入栈顺序和出栈顺序相反
            if(node.left != null) stack.push(node.left);
            if(node.right != null) stack.push(node.right);
        }

        // 反转结果
        Collections.reverse(res);
    }


    public static void main(String[] args) {
        //       1
        //      / \
        //     2   3
        //    / \
        //   4   5
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);

        Hot94_inorderTraversal solution = new Hot94_inorderTraversal();

        System.out.println("遍历——递归版本 ");
        List<Integer> pre = new ArrayList<>();
        solution.preOrder(root, pre);
        System.out.println("前序遍历（根左右）: " + pre);  // [1, 2, 4, 5, 3]

        List<Integer> mid = new ArrayList<>();
        solution.midOrder(root, mid);
        System.out.println("中序遍历（左根右）: " + mid);  // [4, 2, 5, 1, 3]

        List<Integer> post = new ArrayList<>();
        solution.postOrder(root, post);
        System.out.println("后序遍历（左右根）: " + post);  // [4, 5, 2, 3, 1]

        System.out.println("遍历——迭代版本 ");
        List<Integer> pre_1 = new ArrayList<>();
        solution.preOrder_1(root, pre_1);
        System.out.println("前序遍历（根左右）: " + pre_1);  // [1, 2, 4, 5, 3]

        List<Integer> mid_1 = new ArrayList<>();
        solution.midOrder_1(root, mid_1);
        System.out.println("中序遍历（左根右）: " + mid_1);  // [4, 2, 5, 1, 3]

        List<Integer> post_1 = new ArrayList<>();
        solution.postOrder_1(root, post_1);
        System.out.println("后序遍历（左右根）: " + post_1);  // [4, 5, 2, 3, 1]
    }


}
