package casting;

public class Casting1 {
    public static void main(String[] args) {
        double doubleValue = 1.5;
        int intValue = 0;

        // int -> long -> double 순서로 범위가 작은 것에서 큰 것에 할당할 때에는 Casting을 명시하지 않고 생략할 수 있다.(이 것을 자동 형변환 또는 묵시적 형변환이라고 한다.)
        // 하지만 double -> long -> int 순으로 범위가 큰 것에서 작은 것에 할당하려고 할 때는 자동 형변환이 되지 않기 때문에 에러가 발생한다. 이 경우 값을 할당하려는 변수의 타입에 맞게 할당할 값에 변환할 타입을 명시해주어야 한다.

        intValue = (int) doubleValue; // 명시적 형변환
        System.out.println(intValue);

        System.out.println(10.5); // 10.5
        System.out.println((int) 10.5); // 10 -> int로 형변환 하면 int 타입 변수에는 정수만 담을 수 있기 떄문에 .5는 버려진다.

        System.out.println("doubleValue = " + doubleValue);
        // 1.5 -> 형변환은 형변환을 명시한 해당 코드에서만 현변환 됐을 뿐 실제 doubleValue 변수에 할당된 값에는 변함이 없다.(원시타입이기 때문에 doubleValue 변수에 할당된 값 자체는 변하지 않는다.)
        // 그렇기 떄문에 형변환 한 결과 값을 저장해서 계속 사용하려면 변환할 타입의 변수에 할당해서 사용해야 한다.
    }
}
