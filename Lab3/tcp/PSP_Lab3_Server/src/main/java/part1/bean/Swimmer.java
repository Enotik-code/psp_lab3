package part1.bean;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class Swimmer implements Serializable {
    private Integer number;
    private String name;
    private Long result;

    public Swimmer(Integer number, String name) {
        this.number = number;
        this.name = name;
    }
}
