package com.lt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给定两个字符串 s 和 p，找到 s 中所有 p 的异位词的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
 * <p>
 * 示例 1:
 * 输入: s = "cbaebabacd", p = "abc"
 * 输出: [0,6]
 * 解释:
 * 起始索引等于 0 的子串是 "cba", 它是 "abc" 的异位词。
 * 起始索引等于 6 的子串是 "bac", 它是 "abc" 的异位词。
 * 示例 2:
 * 输入: s = "abab", p = "ab"
 * 输出: [0,1,2]
 * 解释:
 * 起始索引等于 0 的子串是 "ab", 它是 "ab" 的异位词。
 * 起始索引等于 1 的子串是 "ba", 它是 "ab" 的异位词。
 * 起始索引等于 2 的子串是 "ab", 它是 "ab" 的异位词。
 * <p>
 * 提示:
 * 1 <= s.length, p.length <= 3 * 104
 * s 和 p 仅包含小写字母
 */
public class Hot100_滑动窗口_找到字符串中所有字母异位词 {
    public List<Integer> findAnagrams(String s, String p) {
        String hash = hash(p);
        ArrayList<Integer> res = new ArrayList<>();
        int i = 0;
        while (i < s.length() - p.length() + 1) {
            if (p.indexOf(s.charAt(i)) < 0) {
                i++;
                continue;
            }
            String substring = s.substring(i, i + p.length());
            if (hash(substring).equals(hash)) {
                res.add(i);
            }
            i++;
        }
        return res;
    }

    String hash(String p) {
        char[] chars = p.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}
