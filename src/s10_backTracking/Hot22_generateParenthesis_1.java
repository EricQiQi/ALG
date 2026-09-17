package s10_backTracking;

import java.util.ArrayList;
import java.util.List;

/**
 * 22. 括号生成
 */
public class Hot22_generateParenthesis_1 {

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

        if(left < n){
            sb.append('(');
            backTrack(res, sb, n, left+1, right);
            sb.deleteCharAt(sb.length()-1);
        }

        if(left > right){
            sb.append(')');
            backTrack(res, sb, n, left, right+1);
            sb.deleteCharAt(sb.length()-1);
        }
    }


    public static void main(String[] args) {
        Hot22_generateParenthesis_1 solution = new Hot22_generateParenthesis_1();
        List<String> res = solution.generateParenthesis(3);
        System.out.println(res);
    }
}
