package com.lt;

import java.util.Arrays;

/**
 * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
 * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
 * <p>
 * 示例 1：
 * 输入：nums = [100,4,200,1,3,2]
 * 输出：4
 * 解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
 * 示例 2：
 * 输入：nums = [0,3,7,2,5,8,4,6,0,1]
 * 输出：9
 * <p>
 * 提示：
 * 0 <= nums.length <= 105
 * -109 <= nums[i] <= 109
 */
public class Hot100_哈希_最长连续序列 {
    public int longestConsecutive(int[] nums) {
        Arrays.sort(nums);
        int res = 1;
        int index = 0;
        while (index < nums.length) {
            int tmp = 1;
            boolean ser = false;
            while (index + 1 < nums.length && (nums[index] + 1 == nums[index + 1] || nums[index] == nums[index + 1])) {
                if (nums[index] != nums[index + 1]) {
                    tmp += 1;
                }
                res = Math.max(res, tmp);
                index += 1;
                ser = true;
            }
            if (!ser) {
                index += 1;
            }
        }
        return res;
    }
}
