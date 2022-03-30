package com.ssafy.core.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
// 성별로 사용될 코드값
public enum GenderCode implements BaseEnumCode<String> {

    M("Man"),
    W("Woman"),
    MAN("Man"),
    WOMAN("Woman"),
    NULL("");

    private final String value;
}
