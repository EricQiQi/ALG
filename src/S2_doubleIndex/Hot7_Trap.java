package S2_doubleIndex;

/**
 * 接雨水
 */
public class Hot7_Trap {

    /**
     * 双指针解法
     * @param height
     * @return
     */
    public static int trap_1(int[] height){
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

    /**
     * DP 动态规划
     * @param height
     * @return
     */
    public static int trap_2(int[] height){
        if(height == null || height.length == 0) return 0;
        int n = height.length;
        int[] left_max = new int[n];
        int[] right_max = new int[n];

        // 注意赋值
        left_max[0] = height[0];
        right_max[n-1] = height[n-1];


        // 从左往右盖大坝
        for(int i=1; i<n; i++){
            left_max[i] = Math.max(left_max[i-1], height[i]);
        }

        // 从右往左盖大坝
        // 注意从 n-2开始
        for (int j = n-2; j >= 0; j--){
            right_max[j] = Math.max(right_max[j+1], height[j]);
        }

        int res = 0;
        // 翻账本
        for (int i=0; i<n; i++){
            res += Math.min(left_max[i], right_max[i]) - height[i];
        }
        return res;
    }

    public static void main(String[] args) {
        int[] height = {0,1,0,2,1,0,1,3,2,1,2,1};
        System.out.println(trap_1(height));
        System.out.println(trap_2(height));
    }
}
