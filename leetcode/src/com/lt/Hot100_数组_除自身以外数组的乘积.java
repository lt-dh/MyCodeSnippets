package com.lt;

/**
 * 给你一个整数数组 nums，返回 数组 answer ，其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积 。
 * <p>
 * 题目数据 保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内。
 * <p>
 * 请 不要使用除法，且在 O(n) 时间复杂度内完成此题。
 * <p>
 * 示例 1:
 * 输入: nums = [1,2,3,4]
 * 输出: [24,12,8,6]
 * <p>
 * 示例 2:
 * 输入: nums = [-1,1,0,-3,3]
 * 输出: [0,0,9,0,0]
 */
public class Hot100_数组_除自身以外数组的乘积 {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] left = new int[n];
        int[] right = new int[n];
        int leftC = 1;
        for (int i = 0; i < n; i++) {
            leftC *= nums[i];
            left[i] = leftC;
        }
        int rightC = 1;
        for (int i = n - 1; i > -1; i--) {
            rightC *= nums[i];
            right[i] = rightC;
        }

        for (int i = 0; i < n; i++) {
            int leftNum = 1;
            int rightNum = 1;
            if (i - 1 < 0) {
                rightNum = right[i + 1];
            } else if (i == n - 1) {
                leftNum = left[i - 1];
            } else {
                leftNum = left[i - 1];
                rightNum = right[i + 1];
            }
            nums[i] = leftNum * rightNum;
        }
        return nums;
    }
}
