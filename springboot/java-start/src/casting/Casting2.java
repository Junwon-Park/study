package casting;

public class Casting2 {
    public static void main(String[] args) {
        long maxIntValue = 2147483647; // 2147483647은 int 타입에 담을 수 있는 범위 중 최고 큰 값.(2147483648 할당 붏가 -> 에러 발생)
        long maxIntOver = 2147483648L; // long 타입은 값에 L을 붙여줘야 long으로 인식하게 되고 long은 int의 2147483647 보다 최고 값이 더 크기 때문에 여기에 할당된 값에 에러가 발생하지 않고 정상 동작하게 된다. -> long 타입에 할당할 값에 L을 안붙이면 int의 값 범위가 적용된다.
        int intValue = 0;

        intValue = (int) maxIntValue;
        System.out.println(intValue); // intValue가 int 타입의 변수이기 떄문에 문제 없이 2147483647 출력

        intValue = (int) maxIntOver; // maxIntOver는 int 타입에 할당할 수 있는 범위를 넘었기 때문에 할당할 수 없지만, int로 형변환을 하면 할당이 가능하긴 하다.
        System.out.println(intValue); // 하지만 maxIntOver는 int 타입에 할당할 수 있는 범위를 넘었기 때문에 int의 가장 작은 값인 -2147483648이 할당되는 문제가 발생한다.

        // 만약 maxIntOver의 값이 2147483649L이라면 (int) maxIntOver은 -2147483647이 된다.
        // 마치 시계 처럼 12시를 넘게 되면 다시 처음 부터 시작되는 것과 같다.
        // 이런 현상을 오버플로우라고 한다.
        maxIntOver = 2147483649L;
        intValue = (int) maxIntOver;
        System.out.println(intValue); // -2147483647

        // 하지만 오버플로우가 났을 때, 그 값을 원하는 값이 되도록 혹은 어떤 값이 될지 계산하지 말고 int 타입의 범위를 벗어난 값을 담을 수 있는 타입인 long으로 바꿔주는 것이 좋다.
        // 그러므로 얼마를 초과했을 때 변환된 값은 얼마이고 이런 것을 계산하거나 외울 필요가 없고 오버플로우가 나면 어떻게 되고 이 경우 변수의 타입을 오버플로우가 나지 않는 타입으로 바꿔주는 것이 옳다.
    }
}
