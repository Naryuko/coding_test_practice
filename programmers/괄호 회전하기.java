// 너무 어렵게 생각했다. 길이가 1000밖에 안 되는 만큼 단순한 구현으로도 충분했다. 

import java.util.Stack;

class Solution {
    public int solution(String s) {
        int answer = 0;
        Stack<Character> stack = new Stack<>();
        String temp = s;

        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < temp.length(); j++) {
                char get = temp.charAt(j);
                if (get == '(' || get == '{' || get == '[') {
                    stack.add(temp.charAt(j));
                } else if (!stack.isEmpty() && get == ')' && stack.peek() == '(') {
                    stack.pop();
                } else if (!stack.isEmpty() && get == '}' && stack.peek() == '{') {
                    stack.pop();
                } else if (!stack.isEmpty() && get == ']' && stack.peek() == '[') {
                    stack.pop();
                } else {
                    stack.add('4');
                }
            }

            if (stack.isEmpty()) {
                answer++;
            } else {
                stack.clear();
            }
            char a = temp.charAt(0);
            temp = temp.substring(1) + a;
        }
        return answer;
    }
}
