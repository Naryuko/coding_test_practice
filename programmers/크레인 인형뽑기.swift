import Foundation

func solution(_ board:[[Int]], _ moves:[Int]) -> Int {
    var board = board
    var ret: Int = 0
    var arr: [Int] = []
    
    for j in moves {
        let i = j-1
        let index = getIndex(board, i)
        if index[0] == -1 { continue }
        board[index[0]][i] = 0
        if arr.last == index[1] {
            ret += 2
            arr.removeLast()
        } else {
            arr.append(index[1])
        }
    }
    
    return ret
}

func getIndex (_ list: [[Int]], _ col: Int) -> [Int] {
    for i in 0..<list.count {
        if list[i][col] != 0 {
            return [i, list[i][col]] 
        }
    }
    return [-1, -1]
}
