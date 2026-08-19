package test;

import java.util.Arrays;
import java.util.Random;

public class QuickSort {
    public static void quickSort(int[] arr){
        if (arr == null || arr.length == 0){
            return;
        }
        quickSort(arr, 0, arr.length-1);
    }

    public static void quickSort(int[] arr, int left, int right){
        if (left >= right){
            return;
        }
        // 找基准
        int pivotIndex = partition(arr, left, right);
        // 递归
        quickSort(arr, left, pivotIndex-1);
        quickSort(arr, pivotIndex+1, right);
    }

    public static int partition(int[] arr, int left, int right){
       int p = left + new Random().nextInt(right-left+1);
       swap(arr, left, p);

       // 基准值变了，基准的index变成 left
       int pivot = arr[left];
       int i = left;
       int j = right;

       while(i<j){
           while(i<j && arr[j] >= pivot){
               j--;
           }
           while(i<j && arr[i] <= pivot){
               i++;
           }
           if(i<j){
               swap(arr, i, j);
           }
       }
       // 将基准元素交换过去，注意：基准元素的index是left
       swap(arr, left, i);
       return i;
    }


    public static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    public static void main(String[] args){
        int[] arr = {3, 6, 8, 10, 1, 2, 1};
        quickSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
