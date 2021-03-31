public class Solution {
    public int solution(String s) {
        int answer = Integer.MAX_VALUE;
        for (int i = 1; i <= s.length()/2+1; i++) {
            answer = Math.min(answer, check(s, i));
        }
        return answer;
    }

//    public static void main (String[] arg) {
//        String a = "aaaaaaaaaab";
//        Solution x = new Solution();
//        System.out.println(x.solution(a));
//    }

    public static int check (String s, int len) {
        int min = s.length();
        int flag = 1;
        int index = 0;

        while (index + 2*len - 1 < s.length()) {
            String a = s.substring(index, index+len);
            String b = s.substring(index+len, index+len*2);
            if (a.equals(b)) {
                flag++;
                min -= len;
            } else if (flag > 1) {
                min += get(flag);
                flag = 1;
            }
            index += len;
        }

        if (flag > 1) {
            min += flag/10 + 1;
        }

        return min;
    }
    
    public static int get (int n) {
        if (n/10 == 0) {
            return 1;
        }
        
        return get(n/10) + 1;
    }
}
