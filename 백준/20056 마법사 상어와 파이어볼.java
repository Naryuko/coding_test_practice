import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.LinkedList;

// LinkedList를 원소로 가지는 2차원 배열을 LinkedList<>[][]로 선언할 수 있다!

public class Main {

    static int[] dRow = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dCol = {0, 1, 1, 1, 0, -1, -1, -1};

    public static void main (String[] arg) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer tk = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(tk.nextToken());
        int M = Integer.parseInt(tk.nextToken());
        int K = Integer.parseInt(tk.nextToken());

        LinkedList<fireball>[][] board = new LinkedList[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = new LinkedList<>();
            }
        }

        for (int q = 0; q < M; q++) {
            tk = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(tk.nextToken())-1;
            int col = Integer.parseInt(tk.nextToken())-1;
            int mass = Integer.parseInt(tk.nextToken());
            int speed = Integer.parseInt(tk.nextToken());
            int direction = Integer.parseInt(tk.nextToken());
            board[row][col].add(new fireball(row,col,mass,speed,direction,0));
        }

        for (int k = 0; k < K; k++) {
            move(board, N, k);
        }

        int totalMass = massCheck(board);

        bw.write(Integer.toString(totalMass));
        bw.close();
        br.close();
    }

    static int massCheck (LinkedList<fireball>[][] board) {
        int ret = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j].size() > 0) {
                    for (int k = 0; k < board[i][j].size(); k++) {
                        ret += board[i][j].get(k).mass;
                    }
                }
            }
        }
        return ret;
    }

    static void move (LinkedList<fireball>[][] board, int N, int k) {
      for (int i = 0; i < N; i++) {
          for (int j = 0; j < N; j++) {
              if (board[i][j].size() > 0) {
                moveBall(board, i, j, N, k);
              }
          }
      }

      for (int i = 0; i < N; i++) {
          for (int j = 0; j < N; j++) {
              if (board[i][j].size() > 1) {
                  split(board, i, j, k);
              }
          }
      }
    }

    // 감소하는 질량 값이 return. 변환 결과 질량이 2가 감소한다면 함수 return은 2이다.
    static void split(LinkedList<fireball>[][] board, int row, int col, int k) {
        int size = board[row][col].size();
        int totalMass = 0;
        int totalSpeed = 0;
        int[] dir = new int[size];
        for (int i = 0; i < size; i++) {
            fireball temp = board[row][col].get(i);
            totalMass += temp.mass;
            totalSpeed += temp.speed;
            dir[i] = temp.direction;
        }
        board[row][col].clear();

        int afterMass = totalMass/5;
        if (afterMass == 0) {
            return;
        }

        totalSpeed /= size;
        int[] direction = check(dir);
        for (int i = 0; i < 4; i++) {
            board[row][col].add(new fireball(row, col, afterMass, totalSpeed, direction[i], k+1));
        }

        return;
    }

    static int[] check (int[] dir) {
        boolean is = true; // 모두 홀짝이 같으면 true, 하나라도 다르면 false
        int first = dir[0]%2;
        for (int i = 1; i < dir.length; i++) {
            if (first != dir[i]%2) {
                is = false;
                break;
            }
        }

        if (is) {
            int[] temp = {0, 2, 4, 6};
            return temp;
        } else {
            int[] temp = {1, 3, 5, 7};
            return temp;
        }
    }

    static void moveBall (LinkedList<fireball>[][] board, int row, int col, int N, int k) {
        int size = board[row][col].size();
        for (int i = size-1; i >= 0; i--) {
            fireball temp = board[row][col].get(i);
            if (temp.howMany == k) {
                board[row][col].remove(i);
                temp.move(N);
                board[temp.row][temp.col].add(temp);
            }
        }
    }


    static class fireball {
        int row;
        int col;
        int mass;
        int speed;
        int direction;
        int howMany;

        public fireball(int row, int col, int mass, int speed, int direction, int howMany) {
            this.row = row;
            this.col = col;
            this.mass = mass;
            this.speed = speed;
            this.direction = direction;
            this.howMany = howMany;
        }

        public void move (int N) {
            for (int i = 0; i < this.speed; i++) {
                this.row = (this.row + dRow[this.direction])%N;
                this.col = (this.col + dCol[this.direction])%N;
                if (this.row < 0) {
                    this.row += N;
                }
                if (this.col < 0) {
                    this.col += N;
                }
            }
            this.howMany++;
        }
    }

}
