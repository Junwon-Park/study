package com.group.libraryapp.dto.user.request;

public class UserCreateRequest {

    private String name;

    private Integer age; // int는 null을 받을 수 없기 떄문에 Nullable 필드인 age는 null을 받을 수 있는 Integer 타입으로 정의한다.

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
