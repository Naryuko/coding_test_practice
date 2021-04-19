import Foundation

func solution(_ board:[[Int]]) -> Int
{
    var board = board
    var answer = 0
    for i in 0..<board.count {
        if board[i][0] == 1 {
            answer = 1
            break
        }
    }
    for i in 0..<board[0].count {
        if board[0][i] == 1 {
            answer = 1
            break
        }
    }
    
    if board.count == 1 || board[0].count == 1 {
        return 1
    }

    
    for i in 1..<board.count {
        for j in 1..<board[0].count {
            if board[i][j] == 0 { continue }
            let left = board[i][j-1]
            let up = board[i-1][j]
            let leftup = board[i-1][j-1]
            if left > 0 && up > 0 && leftup > 0 {
                board[i][j] = min(up, min(left, leftup)) + 1
                answer = max(answer, board[i][j])
            }
        }
    }

    return answer*answer
}
