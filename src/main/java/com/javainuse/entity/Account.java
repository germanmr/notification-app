package com.javainuse.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //    @Version
//    private Integer version;
    private String pin;
    private long balance;

    public Account() {
    }

    public Account(String pin, long balance) {
        this.pin = pin;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return balance == account.balance &&
                Objects.equals(id, account.id) &&
                Objects.equals(pin, account.pin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pin, balance);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append("id=").append(id);
        sb.append(", pin='").append(pin).append('\'');
        sb.append(", balance=").append(balance);
        sb.append('}');
        return sb.toString();
    }
}


