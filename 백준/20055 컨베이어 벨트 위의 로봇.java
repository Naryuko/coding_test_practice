import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    static int upPoint = 0;
    static int downPoint;
    static int[] beltDur;
    static boolean[] beltRobot;
    static int stage = 0;
    static int N;
    static int numOfZero = 0;

    public static void main (String[] arg) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer tk = new StringTokenizer(br.readLine());

        N = Integer.parseInt(tk.nextToken());
        int K = Integer.parseInt(tk.nextToken());
        beltDur = new int[2*N];
        beltRobot = new boolean[2*N];
        downPoint = N-1;

        tk = new StringTokenizer(br.readLine());
        for (int i = 0; i < N*2; i++) {
            int temp = Integer.parseInt(tk.nextToken());
            beltDur[i] = temp;
            if (temp == 0) {
                numOfZero++;
            }
        }

        boolean isEnd = true;

        while (isEnd) {
            isEnd = move(K);
        }

        bw.write(Integer.toString(stage));
        bw.close();
        br.close();
    }

    static boolean move (int K) {
        stage++;
        movePoint();
        moveRobot();

        if (!beltRobot[upPoint] && beltDur[upPoint] > 0) {
            beltRobot[upPoint] = true;
            beltDur[upPoint]--;
            if (beltDur[upPoint] == 0) {
                numOfZero++;
            }
        }

        if (numOfZero >= K) {
            return false;
        } else {
            return true;
        }

    }

    static void moveRobot() {
        if (beltRobot[downPoint]) {
            beltRobot[downPoint] = false;
        }

        for (int i = N-2; i >= 0; i--) {
            int current = i+upPoint;
            int next = i+upPoint+1;
            if (current > 2*N-1) {
                current -= 2*N;
            }
            if (next > 2*N-1) {
                next -= 2*N;
            }
            if (beltRobot[current] && !beltRobot[next] && beltDur[next] > 0) {
                beltRobot[next] = true;
                beltRobot[current] = false;
                beltDur[next]--;
                if (beltDur[next] == 0) {
                    numOfZero++;
                }
            }
        }
    }

    static void movePoint () {
        if (beltRobot[downPoint]) {
            beltRobot[downPoint] = false;
        }

        upPoint--;
        downPoint--;
        if (upPoint < 0) {
            upPoint += 2*N;
        }
        if (downPoint < 0) {
            downPoint += 2*N;
        }
    }


}
