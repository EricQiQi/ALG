package S2_doubleIndex;

/**
 * 接雨水
 */
public class Hot7_Trap {

    public static int trap(int[] height){
        if (height == null || height.length == 0) return 0;

        // 左边见过的最高的，右边见过的最高的板子
        int leftmax = 0, rightmax = 0;
        // 双指针
        int left = 0, right = height.length -1;
        // 记录结果
        int res = 0;

        while(left < right){
            leftmax = Math.max(leftmax, height[left]);
            rightmax = Math.max(rightmax, height[right]);

            // 表示右边一定有更高的板子兜底
            if (height[left] < height[right]){
                res += leftmax - height[left];
                left++;

            // 表示左边一定有更高的板子兜底
            }else{
                res += rightmax - height[right];
                right--;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] height = {0,1,0,2,1,0,1,3,2,1,2,1};
        System.out.println(trap(height));
    }
}
