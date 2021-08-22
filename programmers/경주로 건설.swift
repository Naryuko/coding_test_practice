// board도 queue에 저장해 모든 경우의 수에 대해 bfs를 할 경우 시간초과가 발생했다.
// 항상 board는 큐에 넣지 않으며 최소한의 탐색만 할 방법을 생각하자.

import Foundation

let dRow = [-1, 1, 0, 0]
let dCol = [0, 0, -1, 1]

func solution(_ board:[[Int]]) -> Int {
    let answer = bfs(board, board.count)
    
    return answer
}

func bfs (_ board: [[Int]], _ n: Int) -> Int {
    var answer = Int.max
    var board = board
    let currentQueue = Queue<[Int]>() // [row, col, dir, cost]
    
    board[0][0] = 1
    currentQueue.add([0,0,-1,0])
    
    while !currentQueue.isEmpty() {
        let current = currentQueue.poll()
        
        for i in 0...3 {
            let nRow = current[0] + dRow[i]
            let nCol = current[1] + dCol[i]
                        
            if nRow < 0 || nCol < 0 || nRow >= n || nCol >= n {
                continue
            }
                        
            let cost = (current[2] != -1 && current[2] != i) ? current[3] + 600 : current[3] + 100
            
            if board[nRow][nCol] != 0 && cost > board[nRow][nCol] {
                continue
            }

            if cost > answer { continue }
            
            if nRow == n-1 && nCol == n-1 {
                answer = min(answer, cost)
                continue
            }
            
            let dir = i
            board[nRow][nCol] = cost
            currentQueue.add([nRow, nCol, dir, cost])
            
        }
        
        
    }
    
    return answer
    
}


class Queue<T> {
    private var left: [T] = []
    private var right: [T] = []
    
    func add (_ num: T) {
        self.right.append(num)
    }
    
    func poll() -> T {
        if self.left.count == 0 {
            self.left = self.right.reversed()
            self.right.removeAll()
        }
        
        return self.left.removeLast()
    }
    
    func isEmpty() -> Bool {
        return self.left.count == 0 && self.right.count == 0
    }
}

