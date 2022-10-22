package br.com.gusta.springkafka.consumer.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class City implements Serializable {

    private String name;
    private String uf;

}
