import java.util.Arrays;

class Solution
{
    public int solution(int []A, int []B)
    {
        int answer = 0;

        Arrays.sort(A);
        Arrays.sort(B);
        for (int i = 0; i < A.length; i++) {
            int indexB = B.length-1-i;
            answer += A[i]*B[indexB];
        }

        return answer;
    }
}
