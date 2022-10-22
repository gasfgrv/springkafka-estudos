package br.com.gusta.springkafka.producer.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class City implements Serializable {

    private String name;
    private String uf;

}
