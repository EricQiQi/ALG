package s8_tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 101. 对称二叉树
 */
public class Hot101_isSymmetric {

    /**
     * 方法一：前序遍历和自创的对称遍历，比较两个遍历结果是否相等
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    public boolean isSymmetric_1(TreeNode root) {
        if (root == null) return true;

        List<Integer> leftList = new ArrayList<>();
        preOrder(root.left, leftList);
        List<Integer> rightList = new ArrayList<>();
        postOrder(root.right, rightList);
        return leftList.equals(rightList);
    }

    /**
     * 前序遍历：根 左 右
     */
    private void preOrder(TreeNode root, List<Integer> res) {
        if (root == null) {
            res.add(-101);
        } else {
            res.add(root.val);
            preOrder(root.left, res);
            preOrder(root.right, res);
        }
    }

    /**
     * 自创的对称遍历，根 右 左
     */
    private void postOrder(TreeNode root, List<Integer> res) {
        if (root == null) {
            res.add(-101);
        } else {
            res.add(root.val);
            postOrder(root.right, res);
            postOrder(root.left, res);
        }
    }

    /**
     * 方法二：递归
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    public boolean isSymmetric_2(TreeNode root) {
        return check(root.left, root.right);
    }

    public boolean check(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if(p == null || q == null) return false;
        return p.val == q.val && check(p.left, q.right) && check(p.right, q.left);
    }

    /**
     * 方法三：迭代
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    public boolean isSymmetric_3(TreeNode root){
        return check_3(root, root);
    }

    public boolean check_3(TreeNode p, TreeNode q){
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(p);
        queue.offer(q);
        while(!queue.isEmpty()){
            TreeNode u = queue.poll();
            TreeNode v = queue.poll();
            if (u== null && v == null) continue;
            if (u== null || v == null || u.val != v.val) return false;
            queue.offer(u.left);
            queue.offer(v.right);

            queue.offer(u.right);
            queue.offer(v.left);
        }
        return true;
    }

    public static void main(String[] args) {
        //       1
        //      / \
        //     2   2
        //    / \ / \
        //   4  5 5  4
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(2);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(5);
        root.right.right = new TreeNode(4);

        Hot101_isSymmetric solution = new Hot101_isSymmetric();
        System.out.println(solution.isSymmetric_1(root));
        System.out.println(solution.isSymmetric_2(root));
        System.out.println(solution.isSymmetric_3(root));
    }
}
