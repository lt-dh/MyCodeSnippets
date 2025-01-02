package com.lt;

import java.util.Arrays;

/**
 * 给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数。
 * 请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
 * <p>
 * 示例 1：
 * 输入：nums = [1,2,0]
 * 输出：3
 * 解释：范围 [1,2] 中的数字都在数组中。
 * <p>
 * 示例 2：
 * 输入：nums = [3,4,-1,1]
 * 输出：2
 * 解释：1 在数组中，但 2 没有。
 * <p>
 * 示例 3：
 * 输入：nums = [7,8,9,11,12]
 * 输出：1
 * 解释：最小的正数 1 没有出现。
 */
public class Hot100_数组_缺失的第一个正数 {
    public int firstMissingPositive(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int i = Integer.MIN_VALUE;
        for (int j = 0; j < n; j++) {
            if (nums[j] == 1) {
                i = j;
                break;
            }
            if (nums[j] > 1) {
                break;
            }
        }
        if (i == Integer.MIN_VALUE) {
            return 1;
        }
        int res = 1;
        for (int j = i + 1; j < n; j++) {
            if (nums[j] > ++res) {
                return res;
            }
            if (nums[j] < res) {
                res--;
            }
        }
        return res + 1;
    }
}
