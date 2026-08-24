package sort;

import java.util.Arrays;
import java.util.Random;

/**
 * 快速排序
 */
public class QuickSort {

    public static void quickSort(int[] arr) {
        if (arr == null || arr.length == 0) return;

        quickSort(arr, 0, arr.length - 1);
    }

    public static void quickSort(int[] arr, int left, int right) {
        if (left >= right) return;

        int pivotIndex = partition(arr, left, right);
        quickSort(arr, left, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1, right);
    }

    public static int partition(int[] arr, int left, int right) {
        int pivotIndex = left + new Random().nextInt(right - left + 1);
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
            if (l < r) {
                swap(arr, l, r);
            }
        }
        // 基准归位
        swap(arr, l, left);
        return l;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    public static void main(String[] args) {
        int[] arr = {3, 6, 8, 10, 1, 2, 1};
        quickSort(arr, 0, arr.length - 1);
        System.out.println("排序后" + Arrays.toString(arr));
    }
}
