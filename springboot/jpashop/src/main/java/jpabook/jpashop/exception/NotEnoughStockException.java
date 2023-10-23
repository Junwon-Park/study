package jpabook.jpashop.exception;

public class NotEnoughStockException extends RuntimeException {

    public NotEnoughStockException() { // Exception 사용 부분에서 인자로 아무 것도 안넣은 경우 호출
        super();
    }

    public NotEnoughStockException(String message) { // Exception 사용 부분에서 첫 번째 인자만 String 타입으로 넣은 경우 호출
        super(message);
    }

    public NotEnoughStockException(String message, Throwable cause) { // Exception 사용 부분에서 String 타입의 첫 번째 인자와 Throwable 타입의 두 번째 인자(총 두 개)를 넣으면 호출
        super(message, cause);
    }

    // 이하 위 주석과 마찬가지로 들어온 인자에 맞게 호출된다. (모두 동일한 이름의 오버로딩 메서드들이다.)

    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }

    protected NotEnoughStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
