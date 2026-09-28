package s12_stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 155. 最小栈
 * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
 * push(x) —— 将元素 x 推入栈中。
 * pop() —— 删除栈顶的元素。
 * top() —— 获取栈顶元素。
 * getMin() —— 检索栈中的最小元素。
 * <p>
 * 方法2：只用一个栈，通过在栈中存储差值来实现
 * 核心思路：栈里不存原始值，而是存「当前值 - 入栈时的最小值」的差值 diff，
 *          另用一个变量 minvalue 记录当前栈的最小值。
 *          ・diff < 0 ：说明新值比旧最小值还小，它就是新的最小值
 *          ・diff >= 0：最小值没变，原值可以用 minvalue + diff 还原
 *          pop 弹到负差值时，意味着"当前最小值被弹掉了"，
 *          需用 minvalue = minvalue - diff 回滚到上一个最小值。
 * 时间复杂度：O(1)
 * 空间复杂度：O(n)（但只有一个栈，比辅助栈方案省一半）
 * 注意：差值用 long 存，避免 value - minvalue 发生 int 溢出
 *
 */
class Hot155_MinStack_2 {
    /** 差值栈：存 (value - 当时 minvalue)，用 long 防溢出 */
    Deque<Long> stack;
    /** 当前栈中的最小值 */
    long minvalue;

    public Hot155_MinStack_2() {
        stack = new ArrayDeque<>();
    }

    /**
     * 入栈：
     * ・空栈：差值固定存 0（自己减自己），minvalue 直接定为这个值
     * ・非空：存 diff = value - minvalue；若 diff < 0 说明刷新了最小值
     */
    public void push(int value) {
        if (stack.isEmpty()) {
            stack.push(0L);
            minvalue = value;
        } else {
            long diff = value - minvalue;
            stack.push(diff);
            if (diff < 0) {
                minvalue = value;   // 新值更小，更新最小值
            }
        }

    }

    /**
     * 出栈：弹的是正差值时，最小值不受影响；
     * 弹的是负差值时，说明弹掉的正是当前最小值本身，
     * 由 diff = minvalue - 上一个最小值 反推出：上一个最小值 = minvalue - diff
     */
    public void pop() {
        long val = stack.pop();
        if (val < 0) {
            minvalue = minvalue - val;   // 回滚到入栈前的最小值
        }
    }

    /**
     * 栈顶真实值：
     * ・diff < 0 → 栈顶就是当前最小值，直接返回 minvalue
     * ・diff >= 0 → 原值 = minvalue + diff
     */
    public int top() {
        long top = stack.peek();
        return top < 0 ? (int) minvalue : (int) (minvalue + top);
    }

    /** 最小值：就是变量 minvalue 本身 */
    public int min() {
        return (int) minvalue;
    }

    public static void main(String[] args) {
        Hot155_MinStack_2 minStack = new Hot155_MinStack_2();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println(minStack.min());   // 返回 -3.
        minStack.pop();
        System.out.println(minStack.top());      // 返回 0.
        System.out.println(minStack.min());   // 返回 -2.
    }
}
