package com.example.common;

import lombok.Getter;

@Getter
public enum PriceType {

    ONEHOUR("1시간", 4000),
    TWOHOUR("2시간", 5000),
    THREEHOUR("3시간", 6000);

    // 문자열을 저장할 필드
    private String time;
    private Integer price;

    // 생성자 (싱글톤)
    private PriceType(String time, Integer price) {
        this.time = time;
        this.price = price;
    }

    public static PriceType fromTime(String time) {
        for (PriceType priceType : PriceType.values()) {
            if (priceType.getTime().equals(time)) {
                return priceType;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 시간입니다: " + time);
    }

}
