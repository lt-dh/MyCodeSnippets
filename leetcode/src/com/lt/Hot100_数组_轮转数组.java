package com.lt;

/**
 * 给定一个整数数组 nums，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
 * <p>
 * 示例 1:
 * 输入: nums = [1,2,3,4,5,6,7], k = 3
 * 输出: [5,6,7,1,2,3,4]
 * 解释:
 * 向右轮转 1 步: [7,1,2,3,4,5,6]
 * 向右轮转 2 步: [6,7,1,2,3,4,5]
 * 向右轮转 3 步: [5,6,7,1,2,3,4]
 * <p>
 * 示例 2:
 * 输入：nums = [-1,-100,3,99], k = 2
 * 输出：[3,99,-1,-100]
 * 解释:
 * 向右轮转 1 步: [99,-1,-100,3]
 * 向右轮转 2 步: [3,99,-1,-100]
 */
public class Hot100_数组_轮转数组 {
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k%n;
        int hFirst = n - k;
        for(int i = 0;i<hFirst/2;i++){
            int temp = nums[hFirst-1-i];
            nums[hFirst-1-i] = nums[i];
            nums[i] = temp;
        }
        for(int i = hFirst;i<(hFirst+(n-hFirst)/2);i++){
            int temp = nums[n-(i-hFirst)-1];
            nums[n-(i-hFirst)-1] = nums[i];
            nums[i] = temp;
        }
        for(int i = 0;i<n/2;i++){
            int temp = nums[n-i-1];
            nums[n-i-1] = nums[i];
            nums[i] = temp;
        }
    }
}
