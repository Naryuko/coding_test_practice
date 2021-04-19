// static 사용을 주의해라. 전역변수는 이왕이면 static 쓰지 말아보자. 필요할 때만 쓰기

import java.util.HashMap;

class Solution {
    int answer = 0;
    static char[] character = {'A', 'C', 'F', 'J', 'M', 'N', 'R', 'T'};
    static String[] dataa;

    public int solution(int n, String[] data) {
        dataa = data;
        get(new HashMap<>(), new boolean[8], 0);

        return answer;
    }

    public void get (HashMap<Character, Integer> map, boolean[] check, int count) {
        if (count == 8) {
            if (isValid(map)) {
                answer++;
                return;
            }
        }

        for (int i = 0; i < 8; i++) {
            if (!check[i]) {
                map.put(character[i], count);
                check[i] = true;
                get((HashMap<Character, Integer>) map.clone(), check.clone(), count+1);
                check[i] = false;
                map.remove(character[i]);
            }
        }

    }

    public boolean isValid (HashMap<Character, Integer> map) {
        for (String a : dataa) {
            char from = a.charAt(0);
            char to = a.charAt(2);
            char valid = a.charAt(3);
            int num = Integer.parseInt(String.valueOf(a.charAt(4)));

            switch (valid) {
                case '=':
                    if (Math.abs(map.get(from)-map.get(to)) != num+1) {
                        return false;
                    }
                    break;
                case '<':
                    if (Math.abs(map.get(from)-map.get(to)) >= num+1) {
                        return false;
                    }
                    break;
                case '>':
                    if (Math.abs(map.get(from)-map.get(to)) <= num+1) {
                        return false;
                    }
                    break;
            }
        }
        return true;
    }

//    public static void main (String[] arg) {
//        int n = 2;
//        String[] data = {"N~F=0", "R~T>2"};
//        Solution s = new Solution();
//        System.out.println(s.solution(n, data));
//    }
}
