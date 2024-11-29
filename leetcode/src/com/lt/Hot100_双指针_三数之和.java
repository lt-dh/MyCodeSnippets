package com.lt;

import java.util.*;

/**
 * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。请你返回所有和为 0 且不重复的三元组。
 * 注意：答案中不可以包含重复的三元组。
 * <p>
 * 示例 1：
 * 输入：nums = [-1,0,1,2,-1,-4]
 * 输出：[[-1,-1,2],[-1,0,1]]
 * 解释：
 * nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0 。
 * nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0 。
 * nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0 。
 * 不同的三元组是 [-1,0,1] 和 [-1,-1,2] 。
 * 注意，输出的顺序和三元组的顺序并不重要。
 * 示例 2：
 * 输入：nums = [0,1,1]
 * 输出：[]
 * 解释：唯一可能的三元组和不为 0 。
 * 示例 3：
 * 输入：nums = [0,0,0]
 * 输出：[[0,0,0]]
 * 解释：唯一可能的三元组和为 0 。
 * <p>
 * 提示：
 * 3 <= nums.length <= 3000
 * -105 <= nums[i] <= 105
 */
public class Hot100_双指针_三数之和 {

    // 超时
//    public static List<List<Integer>> threeSum(int[] nums) {
//        ArrayList<List<Integer>> res = new ArrayList<>();
//        HashSet<String> set = new HashSet<>();
//        int i = 0;
//        int j = 0;
//        int k = 0;
//        while (i <= nums.length - 1 - 2) {
//            int residueTwo = -nums[i];
//            j = i + 1;
//            while (j <= nums.length - 1 - 1) {
//                int residueOne = residueTwo - nums[j];
//                k = j + 1;
//                while (k <= nums.length - 1) {
//                    if (nums[k] == residueOne) {
//                        ArrayList<Integer> list = new ArrayList<>();
//                        list.add(nums[i]);
//                        list.add(nums[j]);
//                        list.add(nums[k]);
//                        list.sort(Integer::compareTo);
//                        String hash = list.get(0) + "" + list.get(1) + list.get(2);
//                        if (!set.contains(hash)){
//                            set.add(hash);
//                            res.add(list);
//                            break;
//                        }
//                    }
//                    k++;
//                }
//                j++;
//            }
//            i++;
//        }
//        return res;
//    }
    public static List<List<Integer>> threeSum(int[] nums) {
        ArrayList<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        HashSet<String> set = new HashSet<>();
        int i = 0;
        int lastI = Integer.MIN_VALUE;
        while (i < nums.length && nums[i] <= 0) {
            if (lastI == nums[i]) {
                i++;
                continue;
            }
            HashMap<Integer, Integer> map = new HashMap<>();
            int residue = -nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                Integer integer = map.get(nums[j]);
                if (integer == null) {
                    map.put(residue - nums[j], j);
                } else {
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(nums[i]);
                    list.add(nums[j]);
                    list.add(residue - nums[j]);
                    String hash = countHash(list);
                    if (!set.contains(hash)) {
                        set.add(hash);
                        res.add(list);
                    }
                }
            }
            lastI = nums[i];
            i++;
        }
        return res;
    }

    static String countHash(List<Integer> list) {
        list.sort(Integer::compareTo);
        return list.get(0) + "" + list.get(1) + list.get(2);
    }
}
