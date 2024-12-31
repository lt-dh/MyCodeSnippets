package com.lt;


/**
 * 给你一个整数数组 nums 和一个整数 k ，请你统计并返回 该数组中和为 k 的子数组的个数 。
 * 子数组是数组中元素的连续非空序列。
 * <p>
 * 示例 1：
 * 输入：nums = [1,1,1], k = 2
 * 输出：2
 * 示例 2：
 * 输入：nums = [1,2,3], k = 3
 * 输出：2
 * <p>
 * 提示：
 * 1 <= nums.length <= 2 * 104
 * -1000 <= nums[i] <= 1000
 * -107 <= k <= 107
 */
public class Hot100_字串_和为K的子数组 {
    public int subarraySum(int[] nums, int k) {
        int res = 0;
        int i = 0;
        int j = 0;
        while (i < nums.length) {
            int add = 0;
            j = i;
            while (j < nums.length) {
                add += nums[j];
                if (add == k) {
                    res++;
                }
                j++;
            }
            i++;
        }
        return res;
    }
}
