package part1.service;

import part1.bean.Swimmer;

import java.util.*;
import java.util.stream.Collectors;

public class SwimService {

    static List<Swimmer> swimmerList = new ArrayList<>();

    public void setSwimmers() {
        Swimmer swimmer = new Swimmer(1, "Ivanov");
        Swimmer swimmer1 = new Swimmer(2, "Smirnov");
        Swimmer swimmer2 = new Swimmer(3, "Petrov");
        Swimmer swimmer3 = new Swimmer(4, "Semenov");
        swimmerList.add(swimmer);
        swimmerList.add(swimmer1);
        swimmerList.add(swimmer2);
        swimmerList.add(swimmer3);
    }

    public void setResult(int number, long result) {
        swimmerList.forEach(el -> {
            if(el.getNumber().equals(number))
                el.setResult(result);
        });
    }

    public String getPosition(){
        List<Swimmer> newList = swimmerList.stream().sorted(Comparator.comparing(Swimmer::getResult))
                .collect(Collectors.toList());
                return newList.toString();
    }

    public String getSwimmers(){
        return swimmerList.toString();
    }

}
