package com.musinsa.report.parksanhee.domain;

import com.musinsa.report.parksanhee.exception.NotAllowNegativeNumberException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Money {
    public static final Money ZERO = Money.wons(0);
    private static final DecimalFormat WON_FORMATTER = new DecimalFormat("#,##0원");

    @Column(name = "price")
    private BigDecimal amount;

    public static Money wons(long amount) {
        if(amount < 0){
            throw new NotAllowNegativeNumberException("가격은 음수가 될 수 없습니다.");
        }
        return new Money(BigDecimal.valueOf(amount));
    }

    public static Money wons(BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO) < 0){
            throw new NotAllowNegativeNumberException("가격은 음수가 될 수 없습니다.");
        }
        return new Money(amount);
    }


    @Override
    public String toString() {
        return WON_FORMATTER.format(this.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(toString(), money.toString());
    }

    @Override
    public int hashCode() {
        return toString() != null ? toString().hashCode() : 0;
    }
}
