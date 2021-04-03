import java.util.*;

public class Solution {
    char[][] map;
    int mm;
    int nn;
    int[] dRow = {-1, 1, 0, 0}; // 0: 위, 1: 아래
    int[] dCol = {0, 0, -1, 1}; // 2: 왼쪽, 3: 오른쪽

    public String solution(int m, int n, String[] board) {
        StringBuilder answer = new StringBuilder();
        mm = m;
        nn = n;
        HashMap<Character, int[]> targetPosition = new HashMap<>();
        HashMap<Character, int[]> secondPosition = new HashMap<>();
        LinkedList<Character> alist = new LinkedList<>();

        map = new char[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char temp = board[i].charAt(j);
                map[i][j] = temp;
                if (temp != '.' && temp != '*') {
                    if (!targetPosition.containsKey(temp)) {
                        targetPosition.put(temp, new int[] {i, j});
                        alist.add(temp);
                    } else {
                        secondPosition.put(temp, new int[] {i, j});
                    }
                }
            }
        }

        Collections.sort(alist);

        int index = 0;
        while (index < alist.size()) {
            char alphaNow = alist.get(index);
            int[] alphaPosition = targetPosition.get(alphaNow);
            int[] betaPosition = secondPosition.get(alphaNow);

            if (bfs(alphaNow, alphaPosition[0], alphaPosition[1], betaPosition[0], betaPosition[1])) {
                answer.append(alphaNow);
                alist.remove(index);
                index = 0;
            } else {
                index++;
            }
        }

        return alist.size() == 0 ? answer.toString():"IMPOSSIBLE";
    }

    public boolean bfs (char goal, int m, int n, int goalRow, int goalCol) {
        boolean ret = false;

        Queue<int[]> positionQueue = new LinkedList<>();
        Queue<Boolean> turnQueue = new LinkedList<>();
        Queue<Integer> dirQueue = new LinkedList<>();
        Queue<boolean[][]> mapCheckQueue = new LinkedList<>();

        boolean[][] mapCheck = new boolean[mm][nn];
        mapCheck[m][n] = true;

        positionQueue.add(new int[] {m,n});
        mapCheckQueue.add(mapCheck);
        turnQueue.add(false);
        dirQueue.add(0);

        while (!positionQueue.isEmpty()) {
            int[] position = positionQueue.poll();
            boolean hasTurn = turnQueue.poll();
            int dir = dirQueue.poll();
            boolean[][] checkMapT = mapCheckQueue.poll();
            boolean[][] checkMap = new boolean[checkMapT.length][];
            for (int i = 0; i < checkMapT.length; i++) {
                checkMap[i] = checkMapT[i].clone();
            }

            if (position[0] != m || position[1] != n) {
                if (map[position[0]][position[1]] == goal) {
                    map[m][n] = '.';
                    map[position[0]][position[1]] = '.';
                    ret = true;
                    break;
                } else if (map[position[0]][position[1]] != '.'|| checkMap[position[0]][position[1]]) {
                    continue;
                }
            }
            checkMap[position[0]][position[1]] = true;

            if (hasTurn) {
                if (position[0] == goalRow) {
                    int ddir = position[1]<goalCol ? 3:2;
                    if (ddir != dir) {
                        continue;
                    }

                    positionQueue.add(new int[] {position[0], position[1]+dCol[ddir]});
                    turnQueue.add(true);
                    dirQueue.add(ddir);
                    mapCheckQueue.add(checkMap);
                } else if (position[1] == goalCol) {
                    int ddir = position[0]<goalRow ? 1:0;
                    if (ddir != dir) {
                        continue;
                    }

                    if (position[0] + dRow[ddir] >= 0 && position[0] + dRow[ddir] < mm) {
                        positionQueue.add(new int[]{position[0] + dRow[ddir], position[1]});
                        turnQueue.add(true);
                        dirQueue.add(ddir);
                        mapCheckQueue.add(checkMap);
                    }
                }
            } else {
                if (position[0] == goalRow) {
                    int ddir = position[1]<goalCol ? 3:2;

                    if (position[1] + dCol[ddir] >= 0 && position[1] + dCol[ddir] < nn) {
                        positionQueue.add(new int[]{position[0], position[1] + dCol[ddir]});
                        turnQueue.add((position[0] != m || position[1] != n) && dir != ddir);
                        dirQueue.add(ddir);
                        mapCheckQueue.add(checkMap);
                    }
                } else if (position[1] == goalCol) {
                    int ddir = position[0]<goalRow ? 1:0;

                    if (position[0] + dRow[ddir] >= 0 && position[0] + dCol[ddir] < nn ) {
                        positionQueue.add(new int[]{position[0] + dRow[ddir], position[1]});
                        turnQueue.add((position[0] != m || position[1] != n) && dir != ddir);
                        dirQueue.add(ddir);
                        mapCheckQueue.add(checkMap);
                    }
                } else {
                    int row = position[0];
                    int col = position[1];
                    int rDir = row<goalRow ? 1:0;
                    int cDir = col<goalCol ? 3:2;

                    if (position[0] + dRow[rDir] >= 0 && position[0] + dCol[rDir] < nn ) {
                        positionQueue.add(new int[]{row + dRow[rDir], col});
                        turnQueue.add((position[0] != m || position[1] != n) && dir != rDir);
                        dirQueue.add(rDir);
                    }
                    if (position[1] + dCol[cDir] >= 0 && position[1] + dCol[cDir] < nn) {
                        mapCheckQueue.add(checkMap);
                        positionQueue.add(new int[]{row, col + dCol[cDir]});
                        turnQueue.add((position[0] != m || position[1] != n) && dir != cDir);
                        dirQueue.add(cDir);
                        mapCheckQueue.add(checkMap);
                    }
                }
            }

        }

        return ret;
    }


}
