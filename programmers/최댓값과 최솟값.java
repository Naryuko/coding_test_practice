class Solution {
    public String solution(String s) {
        String[] list = s.split(" ");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (String i : list) {
            int temp = Integer.parseInt(i);
            if (temp < min) {
                min = temp;
            }
            if (temp > max) {
                max = temp;
            }
        }
        String answer = String.valueOf(min)+" "+String.valueOf(max);
        return answer;
    }
}
