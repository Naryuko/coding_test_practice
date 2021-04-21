class Solution {
    int[] cache = new int[1000001];

    public int solution(int n) {
        cache[1] = 1;
        cache[2] = 1;
        
        return fibo(n, 2);
    }
    
    public int fibo (int n, int current) {
        if (cache[current] == 0) {
            cache[current] = (cache[current-1] + cache[current-2])%1234567;
        }
        if (current == n) {
            return cache[n];
        }
        
        return fibo(n, current+1);
    }
}
