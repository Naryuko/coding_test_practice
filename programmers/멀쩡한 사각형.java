// java int는 1억이라는 수를 받을 수 없어 처음에 long타입으로 캐스팅해줘서 사용해야 한다. 이건 문제를 잘못 낸게 아닌가? 함수 input으로 long타입을 주었어야 하는 것 아닌가?

class Solution {
    public long solution(int w, int h) {
        long ww = (long) w;
        long hh = (long) h;
        long answer =  ww * (long) hh;
        long big = ww>hh ? ww:hh;
        long small = ww>hh ? hh:ww;
        long temp = get(big, small);
        long a = ww/temp;
        long b = hh/temp;
        
        return answer-(a+b-1)*temp;
    }
    
    public long get (long a, long b) {
        if (a%b == 0) {
            return b;
        }
        
        return get(b, a%b);
    }
}
