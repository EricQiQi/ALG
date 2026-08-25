package test;


import java.util.*;

public class Hot2 {

    /**
     * 单调队列
     *
     * @param nums
     * @param k
     * @return
     */
    public static int[] maxSlidingWindow_1(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) return new int[0];

        int[] res = new int[nums.length - k + 1];
        int n = nums.length;
        int[] leftmax = new int[n];
        int[] rightmax = new int[n];

        for (int i=0; i<n; i++){
            if (i%k == 0){
                leftmax[i] = nums[i];
            }else{
                leftmax[i] = Math.max(leftmax[i-1], nums[i]);
            }
        }

        for (int j=n-1; j>= 0; j--){
            if (j==n-1 || (j+1)%k == 0){
                rightmax[j] = nums[j];
            }else{
                rightmax[j] = Math.max(rightmax[j+1], nums[j]);
            }
        }

        for (int i=0; i<res.length; i++){
            res[i] = Math.max(leftmax[i], rightmax[i+k-1]);
        }


        return res;
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int[] res = maxSlidingWindow_1(nums, 3);
//        int[] res1 = maxSlidingWindow_2(nums, 3);

        printRes(res);
        System.out.println();
        ;
//        printRes(res1);

    }

    private static void printRes(int[] res) {
        for (int i : res) {
            System.out.print(i + " ");
        }
    }
}
