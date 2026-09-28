package s12_stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 84. 柱状图中最大的矩形
 */
public class Hot84_largestRectangleArea {

    /**
     * 方法1：单调栈 + 哨兵
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    public int largestRectangleArea_1(int[] heights) {
        int n = heights.length;
        int[] newHeights = new int[n+2];
        newHeights[0] = 0;
        newHeights[n+1] = 0;
        System.arraycopy(heights, 0, newHeights, 1, n);

        int maxArea = 0;

        Deque<Integer> stack = new ArrayDeque<>();

        for(int i=0; i<newHeights.length; i++){
            while(!stack.isEmpty() && newHeights[i] < newHeights[stack.peek()]){
                int h = newHeights[stack.pop()];
                int leftBoundary = stack.peek();
                int width = i-leftBoundary-1;

                maxArea = Math.max(maxArea, h * width);
            }

            stack.push(i);
        }

        return maxArea;
    }

    /**
     * 方法2：单调栈+虚拟哨兵
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    public int largestRectangleArea_2(int[] heights) {
        int n = heights.length;
        Deque<Integer> stack = new ArrayDeque<>();
        int maxArea = 0;

        for (int i = 0; i <= n; i++) {
            int currentHeight = i == n ? 0 : heights[i];

            while (!stack.isEmpty() && currentHeight < heights[stack.peek()]) {
                int h = heights[stack.pop()];
                int leftBoundary = stack.isEmpty() ? -1 : stack.peek();
                int width = i - leftBoundary - 1;
                maxArea = Math.max(maxArea, width * h);
            }

            stack.push(i);
        }

        return maxArea;
    }



    public static void main(String[] args) {
        Hot84_largestRectangleArea hot84 = new Hot84_largestRectangleArea();
        int[] heights = {2, 1, 5, 6, 2, 3};
        int res1 = hot84.largestRectangleArea_1(heights);
        int res2 = hot84.largestRectangleArea_2(heights);
        System.out.println(res1);
        System.out.println(res2);
    }
}
