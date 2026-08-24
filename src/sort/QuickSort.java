package sort;

import java.util.Arrays;
import java.util.Random;

/**
 * 快速排序
 */
public class QuickSort {

    public static void quickSort(int[] arr){
        if (arr == null || arr.length == 0){
            return;
        }
        quickSort(arr, 0, arr.length-1);
    }

    public static void quickSort(int[] arr, int low, int high){
        // 递归终止条件
        if (low >= high){
            return;
        }
        // 分区，找到基准元素
        int pivotIndex = partition(arr, low, high);
        quickSort(arr, low, pivotIndex-1);
        quickSort(arr, pivotIndex+1, high);
    }

    public static int partition(int[] arr, int left, int right){
        // 基准可以随机，也可以指定
        int pivotIndex = left + new Random().nextInt(right-left+1);
//        int pivotIndex = left;

        swap(arr, left, pivotIndex);

        int pivot = arr[left];

        // 不能写成 i= left+1，这样扫描不到基准值，交换错误【基准值必须参与比较，并放在正确的位置才可以】
        int i=left;
        int j=right;

        while(i<j){
            while(i<j && arr[j]>=pivot){
                j--;
            }
            while(i<j && arr[i]<=pivot){
                i++;
            }
            // 容易遗漏判断
            if (i<j){
                swap(arr, i, j);
            }
        }

        // 将元素放到正确的位置
        swap(arr, left, i);
        return i;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    public static void main(String[] args) {
        int[] arr = {3, 6, 8, 10, 1, 2, 1};
        quickSort(arr, 0, arr.length - 1);
        System.out.println("排序后"+ Arrays.toString(arr));
    }
}
