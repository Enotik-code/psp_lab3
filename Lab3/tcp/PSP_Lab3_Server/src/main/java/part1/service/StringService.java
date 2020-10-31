package part1.service;

public class StringService {

    static double totalSum = 0;

    public Integer getNumberOfSwimmer(String s){
        String[] words = s.split("\\s");
        Integer newResult = 0;
        for(String subStr:words) {
           newResult = Integer.valueOf(subStr.substring(0,1));
        }
        return newResult;
    }

    public Long getResultFromNumber(String s) {
        String[] words = s.split("\\s");
        Long newResult = Long.valueOf(0);
        for(String subStr:words) {
           newResult = Long.valueOf(subStr.substring(2,subStr.length()));
        }
        return newResult;
    }
}
