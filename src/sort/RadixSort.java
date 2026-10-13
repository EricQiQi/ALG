package sort;

/**
 * 基数排序（LSD：从最低位到最高位，逐位做计数排序）
 *
 * 核心思想：不比较元素大小，而是按“位”分配。
 * 先从个位排起，再十位、百位……高位相等时低位已有序 → 整体有序。
 * 时间 O(d·(n+10))，空间 O(n)，稳定。
 */
public class RadixSort {

    /**
     * 基数排序入口
     * @param arr 待排序的非负整数数组
     * 时间复杂度：O(d·n)，d = max 的位数
     * 空间复杂度：O(n)
     */
    public static void radixSort(int[] arr) {
        if (arr == null || arr.length < 2) return;

        // 找最大值，决定需要排几轮（几位就几轮）
        int max = arr[0];
        for (int num : arr) {
            if (num > max) max = num;
        }

        // exp=1 处理个位，exp=10 处理十位，exp=100 处理百位……直到 max/exp==0 停止
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(arr, exp);
        }
    }

    /**
     * 按指定位（exp）做计数排序，是基数排序的单轮操作
     * @param arr 数组
     * @param exp 当前处理的位权（1=个位, 10=十位, 100=百位…）
     */
    private static void countingSortByDigit(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];  // 本轮排序结果
        int[] count = new int[10];  // 下标 0~9，记录当前位每个数字出现的次数

        // 第一步：统计当前位每个数字的出现次数
        for (int i = 0; i < n; i++) {
            int digit = (arr[i] / exp) % 10;  // 取出当前位的数字
            count[digit]++;
        }

        // 第二步：前缀和，count[i] 变为“当前位 ≤ i 的元素总数”，即落位上界
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // 第三步：从后往前遍历放置，保证稳定性（相同位的元素保持原有相对顺序）
        for (int i = n - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];  // 放到该数字对应的最后一个空位
            count[digit]--;                      // 下一个相同位的元素往前放
        }

        // 把本轮结果拷贝回原数组
        System.arraycopy(output, 0, arr, 0, n);
    }

    public static void main(String[] args) {
        // 经典示例：max=802 → 3 位，会进行个、十、百三轮计数排序
        int[] arr = {170, 45, 75, 90, 802, 24, 2, 66};
        radixSort(arr);
        // 输出：2 24 45 66 75 90 170 802
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
}
