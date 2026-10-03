package s13_dump;


import java.util.PriorityQueue;

/**
 * 295. 数据流的中位数
 */
public class Hot295_MedianFinder {

    private PriorityQueue<Integer> leftHeap;
    private PriorityQueue<Integer> rightHeap;

    public Hot295_MedianFinder() {
        // 左边用大顶堆，默认小顶堆，这里使用 lambda 表达式自定义比较器，实现大顶堆
        leftHeap = new PriorityQueue<>((a, b) -> b - a);
        // 右边用小顶堆
        rightHeap = new PriorityQueue<>();
    }

    public void addNum(int num) {
        if (leftHeap.isEmpty() || num <= leftHeap.peek()) {
            leftHeap.offer(num);
        } else {
            rightHeap.offer(num);
        }

        if (leftHeap.size() > rightHeap.size() + 1) {
            rightHeap.offer(leftHeap.poll());
        } else if (rightHeap.size() > leftHeap.size()) {
            leftHeap.offer(rightHeap.poll());
        }
    }

    public double findMedian() {
        if (leftHeap.size() > rightHeap.size()) {
            return leftHeap.peek();
        }

        return (leftHeap.peek() + rightHeap.peek()) / 2.0;
    }

    public static void main(String[] args) {
        Hot295_MedianFinder medianFinder = new Hot295_MedianFinder();
        medianFinder.addNum(1);    // arr = [1]
        medianFinder.addNum(2);    // arr = [1, 2]
        double res1 = medianFinder.findMedian(); // 返回 1.5 ((1 + 2) / 2)
        System.out.println(res1);
        medianFinder.addNum(3);    // arr[1, 2, 3]
        double res2 = medianFinder.findMedian(); // return 2.0
        System.out.println(res2);
    }
}
