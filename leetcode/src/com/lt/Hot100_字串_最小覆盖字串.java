package com.lt;


import java.util.HashMap;
import java.util.HashSet;

/**
 * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
 * <p>
 * 注意：
 * 对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
 * 如果 s 中存在这样的子串，我们保证它是唯一的答案。
 * <p>
 * 示例 1：
 * 输入：s = "ADOBECODEBANC", t = "ABC"
 * 输出："BANC"
 * 解释：最小覆盖子串 "BANC" 包含来自字符串 t 的 'A'、'B' 和 'C'。
 * <p>
 * 示例 2：
 * 输入：s = "a", t = "a"
 * 输出："a"
 * 解释：整个字符串 s 是最小覆盖子串。
 * <p>
 * 示例 3:
 * 输入: s = "a", t = "aa"
 * 输出: ""
 * 解释: t 中两个字符 'a' 均应包含在 s 的子串中，
 * 因此没有符合条件的子字符串，返回空字符串。
 * <p>
 * 提示：
 * m == s.length
 * n == t.length
 * 1 <= m, n <= 105
 * s 和 t 由英文字母组成
 */
public class Hot100_字串_最小覆盖字串 {

    // 265 / 268 超时
//    public String minWindow(String s, String t) {
//        String res = "";
//        HashMap<Character, Integer> hashMap = new HashMap<>();
//        for (int i = 0; i < t.length(); i++) {
//            char c = t.charAt(i);
//            Integer integer = hashMap.getOrDefault(c, 0);
//            hashMap.put(c,++integer);
//        }
//        int slow = 0;
//        int fast = slow;
//        HashMap<Character, Integer> cloneMap;
//        while (slow < s.length() - t.length() + 1) {
//            cloneMap = (HashMap<Character, Integer>) hashMap.clone();
//            if (!cloneMap.containsKey(s.charAt(slow))){
//                slow++;
//                continue;
//            }
//            int tCount = t.length();
//            fast = slow;
//            while (fast < s.length()) {
//                char fastChar = s.charAt(fast);
//                Integer integer = cloneMap.get(fastChar);
//                if (integer == null || integer < 0){
//
//                }else {
//                    cloneMap.put(fastChar,--integer);
//                    tCount--;
//                    if (tCount==0){
//                        if (res == ""){
//                            res = s.substring(slow,fast+1);
//                        }else {
//                            int i = fast - slow + 1;
//                            if (res.length()>i){
//                                res = s.substring(slow,fast+1);
//                            }
//                        }
//                        break;
//                    }
//                }
//                fast++;
//            }
//            slow++;
//        }
//        return res;
//    }
    public String minWindow(String s, String t) {
        if (s == null || s.length() == 0 || t == null || t.length() == 0) {
            return "";
        }

        // 用于记录目标字符串t中每个字符的频次
        HashMap<Character, Integer> targetMap = new HashMap<>();
        for (char c : t.toCharArray()) {
            targetMap.put(c, targetMap.getOrDefault(c, 0) + 1);
        }

        // 用于记录当前窗口中字符的频次
        HashMap<Character, Integer> windowMap = new HashMap<>();

        // 需要满足的字符数量
        int required = targetMap.size();
        // 当前满足的字符数量
        int formed = 0;

        int left = 0, right = 0;
        int minLen = Integer.MAX_VALUE;
        int minLeft = -1;

        // 使用滑动窗口
        while (right < s.length()) {
            char c = s.charAt(right);
            windowMap.put(c, windowMap.getOrDefault(c, 0) + 1);

            // 如果当前字符在t中，并且当前窗口中该字符的数量满足t中的需求，增加formed
            if (targetMap.containsKey(c) && windowMap.get(c).intValue() == targetMap.get(c).intValue()) {
                formed++;
            }

            // 如果窗口已经包含了t的所有字符
            while (left <= right && formed == required) {
                c = s.charAt(left);

                // 更新最小窗口
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    minLeft = left;
                }

                // 收缩窗口
                windowMap.put(c, windowMap.get(c) - 1);
                if (targetMap.containsKey(c) && windowMap.get(c).intValue() < targetMap.get(c).intValue()) {
                    formed--;
                }

                left++;
            }

            // 扩展窗口
            right++;
        }

        return minLeft == -1 ? "" : s.substring(minLeft, minLeft + minLen);
    }
}
