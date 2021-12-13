package org.ilmostro.basic.leetcode.interview;

import java.util.ArrayList;

/**
 * 三合一。描述如何只用一个数组来实现三个栈。
 * <p>
 * 你应该实现push(stackNum, value)、pop(stackNum)、isEmpty(stackNum)、peek(stackNum)方法。stackNum表示栈下标，value表示压入的值。
 * <p>
 * 构造函数会传入一个stackSize参数，代表每个栈的大小。
 * <p>
 * 示例1:
 * <p>
 * 输入：
 * ["TripleInOne", "push", "push", "pop", "pop", "pop", "isEmpty"]
 * [[1], [0, 1], [0, 2], [0], [0], [0], [0]]
 * 输出：
 * [null, null, null, 1, -1, -1, true]
 * 说明：当栈为空时`pop, peek`返回-1，当栈满时`push`不压入元素。
 * 示例2:
 * <p>
 * 输入：
 * ["TripleInOne", "push", "push", "push", "pop", "pop", "pop", "peek"]
 * [[2], [0, 1], [0, 2], [0, 3], [0], [0], [0], [0]]
 * 输出：
 * [null, null, null, null, 2, 1, -1, -1]
 *  
 * <p>
 * 提示：
 * <p>
 * 0 <= stackNum <= 2
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/three-in-one-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author li.bowei
 */
public class TripleInOne {

    public static void main(String[] args) {

//        ["TripleInOne", "peek", "pop", "isEmpty", "push", "pop", "push", "push", "pop", "push", "push", "isEmpty",
//         "pop", "peek", "push", "peek", "isEmpty", "peek", "pop", "push", "isEmpty", "pop", "peek", "peek", "pop",
//         "peek", "isEmpty", "pop", "push", "isEmpty", "peek", "push", "peek", "isEmpty", "isEmpty", "isEmpty", "isEmpty",
//         "peek", "push", "push", "peek", "isEmpty", "peek", "isEmpty", "push", "push", "push", "peek", "peek", "pop", "push",
//         "push", "isEmpty", "peek", "pop", "push", "peek", "peek", "pop", "isEmpty", "pop", "peek", "peek", "push", "push"]
        TripleInOne triple = new TripleInOne(18);
        String[] operator = {"peek", "pop", "isEmpty", "push", "pop", "push", "push", "pop", "push", "push", "isEmpty",
                "pop", "peek", "push", "peek", "isEmpty", "peek", "pop", "push", "isEmpty", "pop", "peek", "peek", "pop",
                "peek", "isEmpty", "pop", "push", "isEmpty", "peek", "push", "peek", "isEmpty", "isEmpty", "isEmpty", "isEmpty",
                "peek", "push", "push", "peek", "isEmpty", "peek", "isEmpty", "push", "push", "push", "peek", "peek", "pop", "push",
                "push", "isEmpty", "peek", "pop", "push", "peek", "peek", "pop", "isEmpty", "pop", "peek", "peek", "push", "push"};
        int[][] arr = {{1}, {2}, {1}, {2, 40}, {2}, {0, 44}, {1, 40}, {0}, {1, 54}, {0, 42}, {0}, {1}, {1}, {0, 56}, {2}, {0}, {2}, {2}, {1, 15}, {1}, {1}, {0}, {2}, {0}, {0}, {1}, {0}, {0, 32}, {0}, {0}, {0, 30}, {2}, {1}, {1}, {0}, {0}, {0}, {0, 56}, {1, 40}, {2}, {0}, {0}, {0}, {2, 11}, {0, 16}, {0, 3}, {2}, {0}, {2}, {0, 39}, {0, 63}, {1}, {2}, {0}, {2, 36}, {1}, {0}, {2}, {1}, {0}, {1}, {2}, {0, 8}, {0, 38}};
        ArrayList<String> list = new ArrayList<>();
        for (String s : operator) {
            switch (s) {
                case "peek":
                    list.add("System.out.println(triple.peek(%d) == %d);");
                    break;
                case "pop":
                    list.add("System.out.println(triple.pop(%d) == %d);");
                    break;
                case "isEmpty":
                    list.add("System.out.println(triple.isEmpty(%d) == %b);");
                    break;
                default:
                    list.add("triple.push(%d,%d);");
                    break;
            }
        }

        Object[] objects = {-1,-1,true,null,40,null,null,44,null,null,false,54,40,null,-1,false,-1,-1,null,false,15,56,-1,56,42,false,42,null,false,32,null,-1,false,false,false,false,30,null,null,-1,false,56,false,null,null,null,11,3,11,null,null,false,-1,63,null,40,39,36,false,39,40,-1,null,null};

        for (int i = 0; i < arr.length; i++) {
            int[] ints = arr[i];
            String s = list.get(i);
            if (ints.length > 1) {
//                System.out.println(String.format(s, ints[0], ints[1]));
            } else {
//                System.out.println(String.format(s, ints[0], objects[i]));
            }
        }

        System.out.println(triple.peek(1) == -1);
        System.out.println(triple.pop(2) == -1);
        System.out.println(triple.isEmpty(1) == true);
        triple.push(2,40);
        System.out.println(triple.pop(2) == 40);
        triple.push(0,44);
        triple.push(1,40);
        System.out.println(triple.pop(0) == 44);
        triple.push(1,54);
        triple.push(0,42);
        System.out.println(triple.isEmpty(0) == false);
        System.out.println(triple.pop(1) == 54);
        System.out.println(triple.peek(1) == 40);
        triple.push(0,56);
        System.out.println(triple.peek(2) == -1);
        System.out.println(triple.isEmpty(0) == false);
        System.out.println(triple.peek(2) == -1);
        System.out.println(triple.pop(2) == -1);
        triple.push(1,15);
        System.out.println(triple.isEmpty(1) == false);
        System.out.println(triple.pop(1) == 15);
        System.out.println(triple.peek(0) == 56);
        System.out.println(triple.peek(2) == -1);
        System.out.println(triple.pop(0) == 56);
        System.out.println(triple.peek(0) == 42);
        System.out.println(triple.isEmpty(1) == false);
        System.out.println(triple.pop(0) == 42);
        triple.push(0,32);
        System.out.println(triple.isEmpty(0) == false);
        System.out.println(triple.peek(0) == 32);
        triple.push(0,30);
        System.out.println(triple.peek(2) == -1);
        System.out.println(triple.isEmpty(1) == false);
        System.out.println(triple.isEmpty(1) == false);
        System.out.println(triple.isEmpty(0) == false);
        System.out.println(triple.isEmpty(0) == false);
        System.out.println(triple.peek(0) == 30);
        triple.push(0,56);
        triple.push(1,40);
        System.out.println(triple.peek(2) == -1);
        System.out.println(triple.isEmpty(0) == false);
        System.out.println(triple.peek(0) == 56);
        System.out.println(triple.isEmpty(0) == false);
        triple.push(2,11);
        triple.push(0,16);
        triple.push(0,3);
        System.out.println(triple.peek(2) == 11);
        System.out.println(triple.peek(0) == 3);
        System.out.println(triple.pop(2) == 11);
        triple.push(0,39);
        triple.push(0,63);
        System.out.println(triple.isEmpty(1) == false);
        System.out.println(triple.peek(2) == -1);
        System.out.println(triple.pop(0) == 63);
        triple.push(2,36);
        System.out.println(triple.peek(1) == 40);
        System.out.println(triple.peek(0) == 39);
        System.out.println(triple.pop(2) == 36);
        System.out.println(triple.isEmpty(1) == false);
        System.out.println(triple.pop(0) == 39);
        System.out.println(triple.peek(1) == 40);
        System.out.println(triple.peek(2) == -1);
        triple.push(0,8);
        triple.push(0,38);
    }

    int[] size = new int[3];
    int[] data;
    int stackSize;

    public TripleInOne(int stackSize) {
        data = new int[stackSize * 3];
        this.stackSize = stackSize;
    }

    public void push(int stackNum, int value) {
        if (size[stackNum] >= stackSize) {
            return;
        }
        data[stackNum * stackSize + size[stackNum]++] = value;
    }

    public int pop(int stackNum) {
        if (size[stackNum] <= 0) {
            return -1;
        }
        return data[stackNum * stackSize + --size[stackNum]];
    }

    public int peek(int stackNum) {
        if (size[stackNum] <= 0) {
            return -1;
        }
        return data[stackNum * stackSize + size[stackNum]];
    }

    public boolean isEmpty(int stackNum) {
        return size[stackNum] <= 0;
    }
}

/*
 * Your TripleInOne object will be instantiated and called as such:
 * TripleInOne obj = new TripleInOne(stackSize);
 * obj.push(stackNum,value);
 * int param_2 = obj.pop(stackNum);
 * int param_3 = obj.peek(stackNum);
 * boolean param_4 = obj.isEmpty(stackNum);
 */
