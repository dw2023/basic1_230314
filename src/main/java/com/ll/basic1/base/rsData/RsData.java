package com.ll.basic1.base.rsData;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RsData {
    private final String resultCode;
    private final String msg;

    public static RsData of(String resultCode, String msg) { //
        return new RsData(resultCode, msg);
    }
}