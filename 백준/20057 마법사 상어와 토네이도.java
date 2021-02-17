import java.io.*;
import java.util.StringTokenizer;

public class Main {

    static int[] dy = {-1, 0, 1, 0};
    static int[] dx = {0, 1, 0, -1};

    public static void main (String[] arg) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int N = Integer.parseInt(br.readLine());
        int[][] board = new int[N][N];
        for (int i = 0; i < N; i++) {
            StringTokenizer tk = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(tk.nextToken());
            }
        }
        int sum = move(board, N);
        bw.write(Integer.toString(sum));
        bw.close();
        br.close();
    }

    static int move (int[][] board, int N) {
        int x = N/2;
        int y = N/2;
        int count = 1;
        int moveDir = 0;
        int sum = 0;

        while (count < N) {
            for (int l = 0; l < count; l++) {
                x += dx[moveDir];
                y += dy[moveDir];
                sum += tornado(board, x, y, N, moveDir);
            }
            if (moveDir%2 == 1) {
                count++;
            }
            moveDir = (moveDir + 1)%4;
        }

        while (y >= 1) {
            y += dy[0];
            sum += tornado(board, x, y, N,0);
        }

        return sum;
    }

    static int tornado (int[][] board, int x, int y, int N, int direction) {
        int left = direction;
        int down = (direction+1)%4;
        int right = (direction+2)%4;
        int up = (direction+3)%4;
        int sum = 0;
        int currentSand = board[x][y];
        int remain = currentSand;

        int temp = currentSand*5/100;
        remain -= temp;
        if (x+2*dx[left] < 0 || y+2*dy[left] < 0 || x+2*dx[left] >= N || y+2*dy[left] >= N) {
            sum += temp;
        } else {
            board[x+2*dx[left]][y+2*dy[left]] += temp;
        }

        temp = currentSand/10;
        remain -= 2*temp;
        if (x+dx[left]+dx[up] < 0 || y+dy[left]+dy[up] < 0 || x+dx[left]+dx[up] >= N || y+dy[left]+dy[up] >= N) {
            sum += temp;
        } else {
            board[x+dx[left]+dx[up]][y+dy[left]+dy[up]] += temp;
        }
        if (x+dx[left]+dx[down] < 0 || y+dy[left]+dy[down] < 0 || x+dx[left]+dx[down] >= N || y+dy[left]+dy[down] >= N) {
            sum += temp;
        } else {
            board[x+dx[left]+dx[down]][y+dy[left]+dy[down]] += temp;
        }

        temp = currentSand*7/100;
        remain -= 2*temp;
        if (x+dx[up] < 0 || y+dy[up] < 0 || x+dx[up] >= N || y+dy[up] >= N) {
            sum += temp;
        } else {
            board[x+dx[up]][y+dy[up]] += temp;
        }
        if (x+dx[down] < 0 || y+dy[down] < 0 || x+dx[down] >= N || y+dy[down] >= N) {
            sum += temp;
        } else {
            board[x+dx[down]][y+dy[down]] += temp;
        }

        temp = currentSand/50;
        remain -= 2*temp;
        if (x+2*dx[up] < 0 || y+2*dy[up] < 0 || x+2*dx[up] >= N || y+2*dy[up] >= N) {
            sum += temp;
        } else {
            board[x+2*dx[up]][y+2*dy[up]] += temp;
        }
        if (x+2*dx[down] < 0 || y+2*dy[down] < 0 || x+2*dx[down] >= N || y+2*dy[down] >= N) {
            sum += temp;
        } else {
            board[x+2*dx[down]][y+2*dy[down]] += temp;
        }

        temp = currentSand/100;
        remain -= 2*temp;
        if (x+dx[right]+dx[up] < 0 || y+dy[right]+dy[up] < 0 || x+dx[right]+dx[up] >= N || y+dy[right]+dy[up] >= N) {
            sum += temp;
        } else {
            board[x+dx[right]+dx[up]][y+dy[right]+dy[up]] += temp;
        }
        if (x+dx[right]+dx[down] < 0 || y+dy[right]+dy[down] < 0 || x+dx[right]+dx[down] >= N || y+dy[right]+dy[down] >= N) {
            sum += temp;
        } else {
            board[x+dx[right]+dx[down]][y+dy[right]+dy[down]] += temp;
        }

        if (x+dx[left] < 0 || y+dy[left] < 0 || x+dx[left] >= N || y+dy[left] >= N) {
            sum += remain;
        } else {
            board[x+dx[left]][y+dy[left]] += remain;
        }
        board[x][y] = 0;

        return sum;
    }
}
