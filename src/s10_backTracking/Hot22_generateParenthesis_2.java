package s10_backTracking;

import java.util.ArrayList;
import java.util.List;

/**
 * 22. 括号生成
 */
public class Hot22_generateParenthesis_2 {

    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        backTrack(res, new StringBuilder(), n, 0, 0);
        return res;
    }

    public void backTrack(List<String> res, StringBuilder sb, int n, int left, int right){
        if(sb.length() == 2 * n){
            res.add(sb.toString());
            return;
        }

        char[] arr = {'(', ')'};
        for(int i=0; i<arr.length; i++){
            char ch = arr[i];
            if(ch == '(' && left >= n) continue;
            if(ch == ')' && right >= left) continue;

            int nextLeft = ch == '(' ? left+1 : left;
            int nextRight = ch == ')' ? right+1 : right;

            sb.append(ch);
            backTrack(res, sb, n, nextLeft, nextRight);
            sb.deleteCharAt(sb.length()-1);
        }
    }


    public static void main(String[] args) {
        Hot22_generateParenthesis_2 solution = new Hot22_generateParenthesis_2();
        List<String> res = solution.generateParenthesis(3);
        System.out.println(res);
    }
}
