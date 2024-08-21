package com.example.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class User {

    private int userUid;
    private String name;
    private String id;
    private String phone;
    private int resttime;
    private int point;

}
