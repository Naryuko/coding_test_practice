// 뒤에서부터 생각하자

import java.util.*;

public class Solution {
    public int solution(int n) {
        int ans = 0;
        int current = n;
        while (current != 1) {
            if (current%2 == 0) {
                current /= 2;
            } else {
                current -= 1;
                ans++;
            }
        }

        return ans+1;
    }
}
