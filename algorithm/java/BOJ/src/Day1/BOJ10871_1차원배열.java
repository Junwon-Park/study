package Day1;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BOJ10871_1차원배열 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int x = Integer.parseInt(sc.nextLine().split(" ")[1]);
        List<Integer> nums = Arrays.stream(sc.nextLine().split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        StringBuilder result = new StringBuilder();

        for (Integer num : nums) {
            if (x > num) {
                result.append(num).append(" ");
            };
        }

        System.out.println(result.substring(0, result.length() - 1));
    }
}
