// 문제 조건을 잘 읽자. 최대 2명만 탈 수 있는 구명보트였다.

import java.util.Arrays;

class Solution {
    public int solution(int[] people, int limit) {
        Arrays.sort(people);
        int answer = 0;
        int start = 0;
        int end = people.length-1;
        while (end>=start) {
            if (people[end]+people[start] <= limit) {
                end--;
                start++;
            } else {
                end--;
            }
            answer++;
        }


        return answer;
    }
}
