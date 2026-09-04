package s8_tree;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * 105. 从前序与中序遍历序列构造二叉树
 * 根据一棵树的前序遍历与中序遍历构造二叉树。
 * 
 */
public class Hot105_buildTree_1 {

    private Map<Integer, Integer> indexMap;

    /**
     * 方法1：递归
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int n = preorder.length;
        indexMap = new HashMap<>();
        // 建立（中序遍历）数值和其索引的映射
        for(int i = 0; i < inorder.length; i++){
            indexMap.put(inorder[i], i);
        }

        return buildTree(preorder, 0, n - 1, inorder, 0, n - 1);
    }

    /**
     * 递归构建二叉树
     * @param preorder 前序遍历
     * @param preLeft
     * @param preRight
     * @param inorder 中序遍历
     * @param inLeft
     * @param inRight
     * @return
     */
    public TreeNode buildTree(int[] preorder, int preLeft, int preRight, int[] inorder, int inLeft, int inRight) {
        if (preLeft > preRight){
            return null;
        }

        // 根节点的值
        int rootval = preorder[preLeft];
        TreeNode root = new TreeNode(rootval);

        // 根节点在中序遍历中的索引
        int rootIndex = indexMap.get(rootval);
        // 得到左子树的节点数量
        int leftsize = rootIndex - inLeft;

        root.left = buildTree(preorder, preLeft + 1, preLeft + leftsize, inorder, inLeft, rootIndex - 1);
        root.right = buildTree(preorder, preLeft+leftsize+1, preRight, inorder, rootIndex+1, inRight);
        return root;
    }


    public static void main(String[] args) {
        // 前序：[3, 9, 20, 15, 7]
        // 中序：[9, 3, 15, 20, 7]
        // 期望构造出：
        //     3
        //    / \
        //   9  20
        //     /  \
        //    15   7
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder = {9, 3, 15, 20, 7};

        TreeNode root = new Hot105_buildTree_1().buildTree(preorder, inorder);

        // 层序遍历验证结果
        if (root == null) {
            System.out.println("buildTree 方法尚未实现");
            return;
        }
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.print(node.val + " ");
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        // 期望输出：3 9 20 15 7
    }
}
