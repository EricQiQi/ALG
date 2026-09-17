package s10_backTracking;

import java.util.ArrayList;
import java.util.List;

/**
 * 17. 电话号码的字母组合
 */
public class Hot17_letterCombinations {

    private final String[] Mapping = {
            "",
            "",
            "abc",
            "def",
            "ghi",
            "jkl",
            "mno",
            "pqrs",
            "tuv",
            "wxyz"
    };

    /**
     * 时间复杂度：O(3^m * 4^n)
     * 空间复杂度：O(m+n)
     */
    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        backTrack(res, digits, 0, new StringBuilder());
        return res;
    }

    public void backTrack(List<String> res, String digits, int index, StringBuilder sb){
        // 条件判断
        if(index == digits.length()){
            res.add(sb.toString());
            return;
        }

        // 得到数字对应的字符串
        char ch = digits.charAt(index);
        String letter = Mapping[ch-'0'];

        // 循环数字对应的字符串
        for(int i=0; i<letter.length(); i++){
            // 添加字符
            sb.append(letter.charAt(i));

            // 处理下一个
            backTrack(res, digits, index+1, sb);

            // 回溯
            sb.deleteCharAt(sb.length()-1);
        }
    }

    public static void main(String[] args) {
        Hot17_letterCombinations hot17_letterCombinations = new Hot17_letterCombinations();
        System.out.println(hot17_letterCombinations.letterCombinations("23"));
    }
}
