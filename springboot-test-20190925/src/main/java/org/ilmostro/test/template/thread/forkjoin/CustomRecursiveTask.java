package org.ilmostro.test.template.thread.forkjoin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author li.bowei on 2019-10-11.
 * @description
 */
public class CustomRecursiveTask extends RecursiveTask<Integer> {

    /**
     * 需要操作的arr
     */
    private Integer[] arr;

    /**
     * 阈值,每一个线程处理的数量
     */
    private static final int THRESHOLD = 20;

    CustomRecursiveTask(Integer[] arr) {
        this.arr = arr;
    }

    @Override
    protected Integer compute() {
        if (arr.length > THRESHOLD) {
            //invokeAll方法会执行compute方法，此处createSubTasks递归调用
            return ForkJoinTask.invokeAll(createSubTasks())
                    //转换成流处理
                    .stream()
                    //等待每一个小任务执行完成
                    .mapToInt(ForkJoinTask::join)
                    .sum();
        } else {
            return processing(arr);
        }
    }

    /**
     * 将arr分成两部分,此方法在compute会一直切至小于阈值
     *
     * @return arr的一半
     */
    private Collection<CustomRecursiveTask> createSubTasks() {
        List<CustomRecursiveTask> dividedTasks = new ArrayList<>();
        dividedTasks.add(new CustomRecursiveTask(
                Arrays.copyOfRange(arr, 0, arr.length / 2)
        ));
        dividedTasks.add(new CustomRecursiveTask(
                Arrays.copyOfRange(arr, arr.length / 2, arr.length)
        ));
        return dividedTasks;
    }

    /**
     * arr长度不足阈值，或者递归到最后的的处理逻辑
     *
     * @param arr 需要处理的数据
     * @return 相加之和
     */
    private Integer processing(Integer[] arr) {
        return Arrays.stream(arr)
                .mapToInt(Integer::intValue)
                .sum();
    }
}
