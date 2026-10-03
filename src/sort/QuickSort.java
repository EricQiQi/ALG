package sort;

import java.util.Arrays;
import java.util.Random;

/**
 * 快速排序
 */
public class QuickSort {

    /**
     * 将Random提升成成员变量
     * Random短时间内生成的随机数随机性会变差，但本算法中随机性要求不高，且随机性主要体现在第一次划分，所以将Random提升成成员变量，避免每次递归创建Random对象，提高效率，且随机性更好。
     */
    private Random random = new Random();

    public void quickSort(int[] arr) {
        if (arr == null || arr.length == 0) return;

        quickSort(arr, 0, arr.length - 1);
    }

    public void quickSort(int[] arr, int left, int right) {
        if (left >= right) return;

        int pivotIndex = partition(arr, left, right);
        quickSort(arr, left, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1, right);
    }

    public int partition(int[] arr, int left, int right) {
        int pivotIndex = left + random.nextInt(right - left + 1);
        swap(arr, left, pivotIndex);
        int pivot = arr[left];
        int l = left;
        int r = right;

        while (l < r) {
            while (l < r && arr[r] >= pivot) {
                r--;
            }
            while (l < r && arr[l] <= pivot) {
                l++;
            }
            // 注意比较
            if (l < r) {
                swap(arr, l, r);
            }
        }
        // 基准归位
        swap(arr, l, left);
        return l;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    public static void main(String[] args) {
        QuickSort qs = new QuickSort();
        int[] arr = {3, 6, 8, 10, 1, 2, 1};
        qs.quickSort(arr, 0, arr.length - 1);
        System.out.println("排序后" + Arrays.toString(arr));
    }
}
