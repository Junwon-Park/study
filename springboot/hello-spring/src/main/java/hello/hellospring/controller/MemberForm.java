package hello.hellospring.controller;

public class MemberForm {
    private String name; // createMemberForm 화면에서 input 창에 입력한 데이터를 이 변수로 받을 것이다.
    // 그렇기 때문에 변수명은 화면에서 데이터를 넘겨줄 때 Key와 동일해야 한다.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
