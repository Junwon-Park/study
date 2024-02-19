package com.group.libraryapp.domain.user;

public class User {
    private String name;

    private Integer age; // int는 null을 받을 수 없기 떄문에 Nullable 필드인 age는 null을 받을 수 있는 Integer 타입으로 정의한다.

    public User(String name, Integer age) throws IllegalAccessException {
        if (name == null || name.isBlank()) {
            throw new IllegalAccessException(String.format("잘못된 name(%s)이 들어왔습니다.", name));
        }
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
