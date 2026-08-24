package test;

import java.util.Arrays;
import java.util.Random;

public class QuickSort {

    public static void quickSort(int[] arr) {
        if (arr == null || arr.length == 0) return;
        quickSort(arr, 0, arr.length - 1);
    }

    public static void quickSort(int[] arr, int low, int high) {
        if (low >= high) return;

        int pivotIndex = partition(arr, low, high);
        quickSort(arr, low, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1, high);
    }

    public static int partition(int[] arr, int left, int right) {
        int pivotIndex = left + new Random().nextInt(right - left + 1);
        swap(arr, left, pivotIndex);
        int pivot = arr[left];

        int i = left;
        int j = right;
        while(i<j){
            while(i<j && arr[j] >= pivot){
                j--;
            }
            while (i<j && arr[i] <= pivot){
                i++;
            }
            if(i<j){
                swap(arr, i, j);
            }
        }
        // 基准归位
        swap(arr, left, i);
        return i;
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }



    public static void main(String[] args) {
//        int[] arr = {3, 1, 2};
        int[] arr = {3, 6, 8, 10, 1, 2, 1};

        quickSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
