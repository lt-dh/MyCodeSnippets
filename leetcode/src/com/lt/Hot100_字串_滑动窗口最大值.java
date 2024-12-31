package com.lt;


import java.util.PriorityQueue;

/**
 * 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
 * 返回 滑动窗口中的最大值 。
 * <p>
 * 示例 1：
 * 输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
 * 输出：[3,3,5,5,6,7]
 * 解释：
 * 滑动窗口的位置                最大值
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 * 1 [3  -1  -3] 5  3  6  7       3
 * 1  3 [-1  -3  5] 3  6  7       5
 * 1  3  -1 [-3  5  3] 6  7       5
 * 1  3  -1  -3 [5  3  6] 7       6
 * 1  3  -1  -3  5 [3  6  7]      7
 * <p>
 * 示例 2：
 * 输入：nums = [1], k = 1
 * 输出：[1]
 * <p>
 * 提示：
 * 1 <= nums.length <= 105
 * -104 <= nums[i] <= 104
 * 1 <= k <= nums.length
 */
public class Hot100_字串_滑动窗口最大值 {

    // 超时
//    public int[] maxSlidingWindow(int[] nums, int k) {
//        int[] res = new int[nums.length - k + 1];
//        PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> o2 - o1);
//        int i = 0;
//        int j = i + k - 1;
//        for (int i1 = 0; i1 < k; i1++) {
//            queue.add(nums[i1]);
//        }
//        res[i] = queue.peek();
//        while (j+1 < nums.length) {
//            j++;
//            queue.add(nums[j]);
//            queue.remove(nums[i]);
//            i++;
//            res[i] = queue.peek();
//        }
//        return res;
//    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] res = new int[nums.length-k+1];
        int n = nums.length;
        int lastMaxPos = 0;
        for(int i = 0;i<k;i++){
            if(nums[i]>=nums[lastMaxPos]){
                lastMaxPos = i;
            }
        }
        res[0] = nums[lastMaxPos];

        int left = 1;
        int right = left+k-1;
        while(right<n){
            if(lastMaxPos>=left&&lastMaxPos<=right){
                if(nums[right]>=nums[lastMaxPos]){
                    lastMaxPos = right;
                }
            }else{
                lastMaxPos = left;
                for(int i = 0;i<k;i++){
                    if(nums[i+left]>= nums[lastMaxPos]){
                        lastMaxPos = i+left;
                    }
                }
            }
            res[left] = nums[lastMaxPos];
            left++;
            right++;
        }

        return res;
    }
}
