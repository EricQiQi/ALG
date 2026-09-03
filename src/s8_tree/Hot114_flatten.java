package s8_tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 114. 二叉树展开为链表
 * 给定一个二叉树，原地将它展开为一个单链表。
 */
public class Hot114_flatten {

    /**
     * 方法1：前序遍历
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     * @param root
     */
    public static void flatten_1(TreeNode root) {
        List<TreeNode> res = new ArrayList<>();
        preOrder(root, res);

        for(int i=1; i<res.size(); i++){
            TreeNode prev = res.get(i-1);
            TreeNode curr = res.get(i);
            prev.left = null;
            prev.right = curr;
        }
    }

    public static void preOrder(TreeNode node, List<TreeNode> res){
        if (node == null) return;
        res.add(node);
        preOrder(node.left, res);
        preOrder(node.right, res);
    }


    /**
     * 方法2：迭代-寻找前驱节点
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     * @param root
     */
    public static void flatten_2(TreeNode root) {
        TreeNode curr = root;
        while(curr != null){
            if (curr.left != null){
                // 找到左子树的最右节点
                TreeNode next = curr.left;
                TreeNode preNode = next;
                while(preNode.right != null){
                    preNode = preNode.right;
                }
                // 将 curr 的右子树接到 preNode 的右子树
                preNode.right = curr.right;
                // 将 curr 的左子树接到 curr 的右子树
                curr.left = null;
                curr.right = next;
            }
            // 移动到右子树
            curr = curr.right;
        }
    }

    public static void main(String[] args) {
        //         1
        //        / \
        //       2   5
        //      / \   \
        //     3   4   6
        TreeNode root = new TreeNode(1,
                new TreeNode(2, new TreeNode(3), new TreeNode(4)),
                new TreeNode(5, null, new TreeNode(6)));

//        new Hot114_flatten().flatten_1(root);
        new Hot114_flatten().flatten_2(root);

        // 展开后沿 right 遍历：1 -> 2 -> 3 -> 4 -> 5 -> 6
        StringBuilder sb = new StringBuilder();
        TreeNode curr = root;
        while (curr != null) {
            sb.append(curr.val);
            if (curr.right != null) sb.append(" -> ");
            curr = curr.right;
        }
        System.out.println(sb.toString());
    }
}
