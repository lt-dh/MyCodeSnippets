package com.lt;

/**
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 * 请注意 ，必须在不复制数组的情况下原地对数组进行操作。
 * <p>
 * 示例 1:
 * 输入: nums = [0,1,0,3,12]
 * 输出: [1,3,12,0,0]
 * 示例 2:
 * 输入: nums = [0]
 * 输出: [0]
 * <p>
 * 提示:
 * 1 <= nums.length <= 104
 * -231 <= nums[i] <= 231 - 1
 */
public class Hot100_双指针_移动零 {

    /**
     * 未优化
     */
    public void moveZeroes(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            int j = i;
            boolean have0 = false;
            while (j - 1 >= 0 && nums[j - 1] == 0) {
                --j;
                have0 = true;
            }
            if (have0) {
                nums[j] = nums[i];
                nums[i] = 0;
            }
        }
    }
}
