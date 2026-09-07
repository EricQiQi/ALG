package sort;

public class BubbleSort {

    /**
     * 冒泡排序
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     */
    public static void bubbleSort(int[] array) {
        int n = array.length;
        for(int i=0; i<n-1; i++){
            // 记录是否发生交换
            Boolean swapFlag = false;

            for(int j=0; j<n-1-i; j++){
                if(array[j] > array[j+1]){
                    swap(array, j, j+1);
                    // 发生交换
                    swapFlag = true;
                }
            }

            if (!swapFlag){
                break;
            }
        }
    }

    public static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] array = {64, 34, 25, 6, 11, 12, 11, 90};
        bubbleSort(array);
        for (int i : array) {
            System.out.print(i + " ");
        }
    }
}
