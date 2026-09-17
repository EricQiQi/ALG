package s9_graph;

/**
 * 208. 实现 Trie (前缀树)
 * <p>
 * 核心结构：每个节点有 26 个孩子槽位（对应 a-z）+ 一个 isEnd 标记（是否有单词在这个节点结尾）。
 * 三大操作都是同一条主循环：从根出发，逐字符往下钻。
 * - insert：槽位为空就新建节点，钻到最后把 isEnd 置 true
 * - search：钻到最后要求节点存在且 isEnd == true（完整单词）
 * - startWith：钻到最后只要节点存在就行（前缀，不管 isEnd）
 * 注：题目标准 API 为 startsWith，本实现命名为 startWith。
 *
 * 复杂度分析时间复杂度：
 * insert：O(L)，其中 L 为单词长度。
 * search：O(L)。startsWith：O(P)，其中 P 为前缀长度。
 * 空间复杂度：O(N * \Sigma)，其中 N 为树中所有字符节点的总数，\Sigma = 26 为字符集大小。
 */
public class Hot208_Trie {

    class Trie{

        /** 26 个孩子槽位，下标 = 字符 - 'a'，为 null 表示这个方向没有路 */
        Trie[] children;
        /** 标记是否有单词恰好在这个节点结尾（区分"完整单词"和"只是前缀"） */
        boolean isEnd;

        Trie(){
            this.children = new Trie[26];
            this.isEnd = false;
        }

        /**
         * 插入单词：从根逐字符往下钻，没路就铺路，钻到最后盖上 isEnd 章
         * 时间复杂度：O(L)，L 为单词长度
         */
        void insert(String word){
            Trie node = this;
            for(int i=0 ;i<word.length(); i++){
                char ch = word.charAt(i);
                // 当前字符方向没路，新建一个节点铺路
                if(node.children[ch-'a'] == null){
                    node.children[ch-'a'] = new Trie();
                }
                // 钻进这个字符对应的子节点
                node = node.children[ch-'a'];
            }
            // 钻到单词末尾，标记"这里是一个完整单词的结尾"
            node.isEnd = true;
        }

        /**
         * 查找完整单词：节点要存在，且 isEnd == true
         * 例：插入 apple 后，search("app") 是 false（只是前缀）
         */
        boolean search(String word){
            Trie node = searchPrefix(word);
            return node != null && node.isEnd;
        }

        /**
         * 公共子过程：沿 word 逐字符往下钻，返回钻到的节点；中途断路返回 null
         * search 和 startWith 都靠它定位到前缀末尾的节点
         */
        private Trie searchPrefix(String word) {
            Trie node = this;
            for(int i=0; i<word.length(); i++){
                char ch = word.charAt(i);
                if(node.children[ch-'a'] == null){
                    return null;
                }
                node = node.children[ch-'a'];
            }
            return node;
        }

        /**
         * 查找前缀：只要沿前缀能钻到底（节点不为 null）就算存在，不要求 isEnd
         * 例：插入 apple 后，startWith("app") 是 true
         */
        boolean startWith(String pre){
            return searchPrefix(pre) != null;
        }
    }

    public static void main(String[] args) {
        Hot208_Trie hot208_trie = new Hot208_Trie();
        // Trie 是非静态内部类，必须先 new 外部类再 .new 创建
        Hot208_Trie.Trie trie = hot208_trie.new Trie();
        trie.insert("apple");
        System.out.println(trie.search("apple"));       // true：完整单词
        System.out.println(trie.startWith("app"));      // true：前缀存在
        System.out.println(trie.search("app"));        // false：只是前缀，不是完整单词
        System.out.println(trie.startWith("apples"));  // false：超出已插入的单词长度
        System.out.println(trie.startWith("appl"));    // true：前缀存在
        System.out.println(trie.startWith("application")); // false：走完了 apple，继续钻 application 时在 i 断路
        System.out.println(trie.startWith("applic"));  // false
    }
}
