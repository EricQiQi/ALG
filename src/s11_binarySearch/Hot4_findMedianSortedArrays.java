package s11_binarySearch;

/**
 * 4. 寻找两个正序数组的中位数
 */
public class Hot4_findMedianSortedArrays {

    /**
     * 二分查找（在较短数组上二分分割线）
     * 时间复杂度：O(log(min(m, n)))
     * 空间复杂度：O(1)
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // 保证 nums1 是较短的数组，使二分范围更小，也避免 j 算出负数越界
        if(nums1.length > nums2.length) return findMedianSortedArrays(nums2, nums1);

        int m=nums1.length, n= nums2.length;

        // 左半部分应含的元素个数；+1 使奇数总数时左半多拿一个（中位数就落在左半最大处）
        int totalLeft = (m+n+1)/2;

        // 在 nums1 上二分“分割线”位置 i：nums1 左半取 i 个，nums2 左半取 j=totalLeft-i 个
        int left=0, right=m;
        while(left <= right){
            int i=left+(right-left)/2;   // nums1 的分割位置（左半元素个数）
            int j=totalLeft-i;           // nums2 的分割位置，两者之和恒为 totalLeft

            // L1/L2 = 两数组左半的最大值；R1/R2 = 两数组右半的最小值
            // 越界时用哨兵：左半空→MIN（不影响 max），右半空→MAX（不影响 min）
            int L1 = i==0 ? Integer.MIN_VALUE : nums1[i-1];
            int L2 = j==0 ? Integer.MIN_VALUE : nums2[j-1];
            int R1 = i==m ? Integer.MAX_VALUE : nums1[i];
            int R2 = j==n ? Integer.MAX_VALUE : nums2[j];

            // 分割合法：左半所有元素 ≤ 右半所有元素（同数组内天然有序，只需交叉比较）
            if(L1 <= R2 && L2 <= R1){
                if((m+n)%2 == 1){
                    return Math.max(L1, L2);   // 总数为奇：中位数 = 左半最大值
                }
                // 总数为偶：中位数 = (左半最大 + 右半最小) / 2
                return (double)(Math.max(L1, L2) + Math.min(R1, R2))/2;
            }

            if(L1 > R2){
                right = i-1;   // nums1 左半取多了（L1 太大），分割线左移
            }else{
                left = i+1;    // nums2 左半取多了（L2 > R1），nums1 分割线右移
            }
        }

        throw new IllegalArgumentException("输入数组非正序");
    }

    public static void main(String[] args) {
        Hot4_findMedianSortedArrays solution = new Hot4_findMedianSortedArrays();
        System.out.println(solution.findMedianSortedArrays(new int[]{1, 3}, new int[]{2}));
    }
}
