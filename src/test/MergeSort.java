package test;

public class MergeSort {

    public static void mergeSort(int[] arr, int left, int right) {
        if(arr == null || arr.length == 0) return;
        if(left >= right) return;

        int mid = left+(right-left)/2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid+1, right);
        merge(arr, left, mid, right);
    }

    public static void merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right-left+1];

        int l=left, r=mid+1, k=0;
        while(l<=mid && r<=right){
            if(arr[l] <= arr[r]){
                temp[k++] = arr[l++];
            }else {
                temp[k++] = arr[r++];
            }
        }

        while(l<=mid) temp[k++]=arr[l++];
        while(r<=right) temp[k++]=arr[r++];
        for(int i=0; i<temp.length; i++){
            arr[left++] = temp[i];
        }
    }


    public static void main(String[] args) {
        int[] array = {3, 4, 1, 2, 5};
        mergeSort(array, 0, array.length-1);
        System.out.println("排序后" + java.util.Arrays.toString(array));
    }
}
