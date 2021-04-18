
class Solution {
    public String solution(int n) {
        String ret = "";
        String[] get = {"4", "1", "2"};

        int num = n;

        while (num > 0) {
            ret = get[num%3] + ret;
            num = num%3 == 0 ? num/3-1:num/3;
        }

        return ret;
    }
}
