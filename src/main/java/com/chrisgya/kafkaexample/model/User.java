package com.chrisgya.kafkaexample.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private String name;

    public String toString(){
        return String.format("User [name=%s]", this.name);
    }
}
