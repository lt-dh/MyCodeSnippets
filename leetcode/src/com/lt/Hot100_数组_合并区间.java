package com.lt;


import java.util.*;

/**
 * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。
 * <p>
 * 示例 1：
 * 输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
 * 输出：[[1,6],[8,10],[15,18]]
 * 解释：区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
 * <p>
 * 示例 2：
 * 输入：intervals = [[1,4],[4,5]]
 * 输出：[[1,5]]
 * 解释：区间 [1,4] 和 [4,5] 可被视为重叠区间。
 * <p>
 * 提示：
 * 1 <= intervals.length <= 104
 * intervals[i].length == 2
 * 0 <= starti <= endi <= 104
 */
public class Hot100_数组_合并区间 {
    public int[][] merge(int[][] intervals) {
        ArrayList<int[]> list = new ArrayList<>();
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
        queue.addAll(Arrays.asList(intervals));
        list.add(queue.poll());
        for (int i = 1; i < intervals.length; i++) {
            int[] poll = queue.poll();
            int[] lE = list.get(list.size() - 1);
            if (poll[0]<=lE[1]){
                if (poll[1]<=lE[1]){

                }else {
                    lE[1] = poll[1];
                }
            }else {
                list.add(poll);
            }
        }
        return list.toArray(int[][]::new);
    }
}
