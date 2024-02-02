package casting;

public class Casting3 {
    public static void main(String[] args) {
        int div1 = 3 / 2;
        System.out.println("div1 = " + div1); // 1.5

        double div2 = 3 / 2;
        System.out.println("div2 = " + div2); // 1.0

        double div3 = 3.0 / 2;
        System.out.println("div3 = " + div3); // 1.5

        double div4 = (double) 3 / 2;
        System.out.println("div4 = " + div4); // 1.5

        int a = 3;
        int b = 2;
        double result = (double) a / b;
        System.out.println("result = " + result); // 1.5

        /**
         * @@@ 대원칙은 아래와 같다.
         * 1. 같은 타입 끼리의 계산은 같은 타입의 결과가 나온다.
         *  -> int + int = int, double + double = double
         * 2. 서로 다른 타입 간의 계산은 둘 중 범위가 더 큰 타입으로 자동 형변환 된다.
         *  -> int + long = long, int + double = double, long + double = double
         *
         *  그래서 위에서 변수 a / b를 double 타입의 변수 result에 할당할 때, (double) a / b로 형변환을 해준 것이다.(int a / int b = int -> double result = (double) int)
         *
         *  3.0 + 2 = 5.0이다. -> 3.0은 double 타입이고 2는 int 타입이다. 둘을 계산하면 범위가 더 큰 타입으로 자동 형변환 되기 떄문에 5.0이라는 결과가 나온다.
         */
    }
}
