package com.cjy.code;
import java.util.Arrays;
import java.util.Random;

public class SequenceTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        int[] is = randomIntSequence(1024);
        Arrays.sort(is);
        for (int i : is) {
            System.out.println(i);
        }

    }

    public static int[] randomIntSequence(int n) {
        if (n <= 0) {
            System.out.println("baocuo");
        }
        int num[] = new int[n];
        for (int i = 0; i < n; i++) {
            num[i] = i;
        }
        if (n == 1) {
            return num;
        }
        Random random = new Random();
        if (n == 2 && random.nextInt(2) == 1) //50%的概率换一下
        {
            int temp = num[0];
            num[0] = num[1];
            num[1] = temp;
        }

        for (int i = 0; i < n + 10; i++) {
            int rindex = random.nextInt(n);//产生0~n-1的随机数
            int mindex = random.nextInt(n);
            int temp = num[mindex];
            num[mindex] = num[rindex];
            num[rindex] = temp;
        }
        return num;
    }

}
