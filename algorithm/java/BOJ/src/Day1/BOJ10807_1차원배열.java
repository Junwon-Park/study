package Day1;

import java.util.Scanner;

public class BOJ10807_1차원배열 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int count = 0;
        int n = sc.nextInt();
        sc.nextLine();
        String[] nums = sc.nextLine().split(" ");

        int v = sc.nextInt();

        if (n < 0) return;

        for (String num : nums) {
            if (Integer.parseInt(num) == v) count++;
        }

        System.out.println(count);
    }
}
