class Solution {
    int[] arr = new int[100000];
    public int solution(int n) {
        int answer = 0;
        int i = 1;
        
        while (i*(i+1)/2 <= n) {
            if ((n-get(i-1))%i == 0) {
                answer++;
            }
            i++;
        }
        return answer;
    }
    
    public int get (int n) {
        if (n == 0) {
            return 0;
        }
        if (arr[n] != 0) {
            return n;
        }
        
        return arr[n] = arr[n-1]+n;
    }
}
