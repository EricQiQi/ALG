package sort;

/**
 * 归并排序
 */
public class MergeSort {

    /**
     * 归并排序主方法：递归拆分数组，再合并
     * 时间复杂度：O(n log n)
     * 空间复杂度：O(n)
     */
    public static void mergeSort(int[] arr, int left, int right) {
        // 递归终止条件：区间长度为 1 或 0
        if (left >= right) return;

        // 二分：将区间分成两半
        int mid = (left + right) / 2;
        // 递归排序左半部分
        mergeSort(arr, left, mid);
        // 递归排序右半部分
        mergeSort(arr, mid + 1, right);
        // 合并两个有序区间
        merge(arr, left, mid, right);
    }

    /**
     * 合并两个有序区间 [left, mid] 和 [mid+1, right]
     * 使用临时数组 temp 辅助合并
     */
    public static int[] merge(int[] arr, int left, int mid, int right) {
        // 临时数组，存放合并结果
        int[] temp = new int[right - left + 1];
        // i: 左区间起点，j: 右区间起点，k: temp 的索引
        int i = left, j = mid + 1, k = 0;
        
        // 两区间逐个比较，小的先放入 temp
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        // 左区间剩余元素全部放入 temp
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        // 右区间剩余元素全部放入 temp
        while (j <= right) {
            temp[k++] = arr[j++];
        }
        // 将 temp 拷贝回原数组
        for (i = left; i <= right; i++) {
            arr[i] = temp[i - left];
        }
        return arr;
    }


    public static void main(String[] args) {
        int[] array = {3, 4, 1, 2, 5};
        mergeSort(array, 0, array.length - 1);
        System.out.println("排序后" + java.util.Arrays.toString(array));
    }
}
