package warmingupclub02.dto.request;

public class SumRequest {
    int[] numbers;



    public SumRequest(int[] numbers) {
        this.numbers = numbers;
    }

    public int[] getNumbers() {
        return numbers;
    }
}
