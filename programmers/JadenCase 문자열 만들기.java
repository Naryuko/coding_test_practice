import java.util.regex.Pattern;

class Solution {
    public String solution(String s) {
        String[] toWork = s.split(" ");
        for (int i = 0; i < toWork.length; i++) {
            if (toWork[i].length() == 0) {
                continue;
            }
            String ss = toWork[i];
            ss = ss.toLowerCase();
            String first = String.valueOf(ss.charAt(0));
            String remain = ss.substring(1);
            if (first.matches("[a-z]")) {
                first = first.toUpperCase();
            }
            toWork[i] = first+remain;
        }
        String answer = "";
        for (String ss : toWork) {
            answer += ss + " ";
        }
        if (s.charAt(s.length()-1) == ' ') {
            answer += " ";
        }
        return answer.substring(0,answer.length()-1);
    }
}
