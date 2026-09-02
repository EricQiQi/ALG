package test;

public class MergeSort {

    public static void mergeSort(int[] arr){
        if(arr == null || arr.length == 0) return;

        mergeSort(arr, 0, arr.length - 1);
    }

    public static void mergeSort(int[] arr, int left, int right){
        if (left >= right) return;
        int mid = (left + right)/2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid+1, right);
        merge(arr, left, mid, right);
    }

    public static void merge(int[] arr, int l, int m, int r){
        int[] temp = new int[r-l+1];
        int i=l, j=m+1, k=0;
        while(i<=m && j<=r){
            if(arr[i] < arr[j]){
                temp[k++] = arr[i++];
            }else{
                temp[k++] = arr[j++];
            }
        }
        while(i<=m){
            temp[k++] = arr[i++];
        }
        while(j<=r){
            temp[k++] = arr[j++];
        }
        for(int p=0; p<temp.length; p++){
            arr[l+p] = temp[p];
        }
    }


    public static void main(String[] args) {
        int[] array = {3, 4, 1, 2, 5};
        mergeSort(array);
        System.out.println("排序后" + java.util.Arrays.toString(array));
    }
}
