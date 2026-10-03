package s14_greedy;


/**
 * 121. 买卖股票的最佳时机
 */
public class Hot121_maxProfit {

    public int maxProfit(int[] prices) {
        // low：遍历到当前位置为止的历史最低买入价
        int low = Integer.MAX_VALUE;
        // res：全局最大利润（当天卖出能赚到的最多钱）
        int res = 0;
        for(int price : prices){
            // 更新历史最低价：今天更便宜就把它当作新的买入点
            low = Math.min(low, price);
            // 假设今天卖出，利润 = 今天价格 - 历史最低买入价，打擂台记录最大值
            res = Math.max(res, price - low);
        }
        return res;
    }

    public static void main(String[] args) {
        Hot121_maxProfit solution = new Hot121_maxProfit();
        int[] prices = {7, 1, 5, 3, 6, 4};
        int maxProfit = solution.maxProfit(prices);
        System.out.println(maxProfit); // 输出 5
    }
}
