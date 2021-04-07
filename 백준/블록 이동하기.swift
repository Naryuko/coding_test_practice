// dfs를 할 경우 시간이 너무 오래걸려 타임아웃
// list.contains는 느리다. 학인을 해야 한다면 Bool을 이용하자.

import Foundation

let dRow: [Int] = [-1, 1, 0, 0]
let dCol: [Int] = [0, 0, -1, 1]

func solution(_ board:[[Int]]) -> Int {
    var answer: Int = 0
    let N: Int = board.count
    let check: [[[Bool]]] = [[[Bool]]] (repeating: [[Bool]] (repeating: [false,false], count: N), count: N)
    
    //dfs(map: board, row1: 0, col1: 0, row2: 0, col2: 1, state: 1, answer: &answer, count: 0, check: check, N: N)
    answer = bfs(map: board, row1: 0, col1: 0, row2: 0, col2: 1, check: check, N: N)
    return answer
}

func bfs (map: [[Int]], row1: Int, col1: Int, row2: Int, col2: Int, check: [[[Bool]]], N: Int) -> Int {
    var answer = 0
    
    var queue = Queue() //row1, col1, row2, col2, state, count
    var check  = check
    
    queue.append([row1, col1, row2, col2, 0, 0])
    
    while !queue.isEmpty {
        let temp = queue.removeFirst()
        
        if (temp[0] == N-1 && temp[1] == N-1) || (temp[2] == N-1 && temp[3] == N-1) {
            answer = temp[5]
            break
        }
        
//        if !check[temp[0]][temp[1]].contains(temp[4]) {check[temp[0]][temp[1]].append(temp[4])}
//        if !check[temp[2]][temp[3]].contains(temp[4]) {check[temp[2]][temp[3]].append(temp[4])}
        
        for i in 0...3 {
            let dx1 = temp[0] + dRow[i]
            let dx2 = temp[2] + dRow[i]
            let dy1 = temp[1] + dCol[i]
            let dy2 = temp[3] + dCol[i]
            
            if dx1 < 0 || dx2 < 0 || dy1 < 0 || dy2 < 0 || dx1 >= N || dx2 >= N || dy1 >= N || dy2 >= N {
                continue
            }
            if map[dx1][dy1] == 1 || map[dx2][dy2] == 1 {
                continue
            }
            if check[dx1][dy1][temp[4]] && check[dx2][dy2][temp[4]] {continue}
            check[dx1][dy1][temp[4]] = true
            check[dx2][dy2][temp[4]] = true

            queue.append([dx1,dy1,dx2,dy2,temp[4],temp[5]+1])
        }
        
        let rot = turn(map: map, row1: temp[0], col1: temp[1], row2: temp[2], col2: temp[3])
        for i in rot {
            let dx1 = i[0]
            if dx1 == -1 {
                break
            }
            let dy1 = i[1]
            let dx2 = i[2]
            let dy2 = i[3]
            let state = i[4]

            if check[dx1][dy1][state] && check[dx2][dy2][state] {continue}
//            if !check[dx1][dy1].contains(temp[4]) {check[dx1][dy1].append(temp[4])}
//            if !check[dx2][dy2].contains(temp[4]) {check[dx2][dy2].append(temp[4])}
            
            queue.append([dx1,dy1,dx2,dy2,state,temp[5]+1])
        }
    }
    return answer
}

// dfs code
/*
func dfs (map: [[Int]], row1: Int, col1: Int, row2: Int, col2: Int, state: Int, answer: inout Int, count: Int, check: [[[Int]]], N: Int) {

    if (row1 == N-1 && col1 == N-1) || (row2 == N-1 && col2 == N-1) {
        answer = min(answer, count)
        return
    } else if answer <= count {
        return
    }
    
    var check = check
    if !check[row1][col1].contains(state) {check[row1][col1].append(state)}
    if !check[row2][col2].contains(state) {check[row2][col2].append(state)}
    for i in 0...3 {
        let dx1 = row1 + dRow[i]
        let dx2 = row2 + dRow[i]
        let dy1 = col1 + dCol[i]
        let dy2 = col2 + dCol[i]
        
        if dx1 < 0 || dx2 < 0 || dy1 < 0 || dy2 < 0 || dx1 >= N || dx2 >= N || dy1 >= N || dy2 >= N {
            continue
        }
        if map[dx1][dy1] == 1 || map[dx2][dy2] == 1 {
            continue
        }
        if check[dx1][dy1].contains(state) /*&& check[dx2][dy2].contains(state)*/ {
            continue
        }
        dfs(map: map, row1: dx1, col1: dy1, row2: dx2, col2: dy2, state: state, answer: &answer, count: count+1, check: check, N: N)
    }
    
    let rot = turn(map: map, row1: row1, col1: col1, row2: row2, col2: col2)
    for i in rot {
        let dx1 = i[0]
        if dx1 == -1 {
            break
        }
        let dy1 = i[1]
        let dx2 = i[2]
        let dy2 = i[3]
        let state = i[4]

        if check[dx1][dy1].contains(state) /*&& check[dx2][dy2].contains(state)*/ {
            break
        }
        dfs(map: map, row1: dx1, col1: dy1, row2: dx2, col2: dy2, state: state, answer: &answer, count: count+1, check: check, N: N)
    }
}
*/

// [row1, col1, row2, col2, state] state: 1: -, 2: l
// 회전 못하는 경우 [-1, -1, -,1, -1] 반환
func turn (map: [[Int]], row1: Int, col1: Int, row2: Int, col2: Int) -> [[Int]] {
    if row1 == row2 {
        return mmm(map: map, col1: col1, col2: col2, row: row1)
    } else if col1 == col2 {
        return lll(map: map, row1: row1, row2: row2, col: col1)
    }
    
    return [[-1,-1,-1,-1,-1]]
}

func mmm (map: [[Int]], col1: Int, col2: Int, row: Int) -> [[Int]] {
    var ret: [[Int]] = []
    let upRow: Int = row + dRow[0]
    let downRow: Int = row + dRow[1]
    
    if downRow >= 0 && downRow < map.count {
        if map[downRow][col1] == 0 && map[downRow][col2] == 0 {
            ret.append([row, col1, downRow, col1,1])
            ret.append([row, col2, downRow, col2,1])
        }
    }
    
    if upRow >= 0 && upRow < map.count  {
        if map[upRow][col1] == 0 && map[upRow][col2] == 0 {
            ret.append([upRow, col1, row, col1,1])
            ret.append([upRow, col2, row, col2,1])
        }
    }
    

    
    return ret
}

func lll (map: [[Int]], row1: Int, row2: Int, col: Int) -> [[Int]] {
    var ret: [[Int]] = []
    let leftCol: Int = col + dCol[2]
    let rightCol: Int = col + dCol[3]
    
    if rightCol >= 0 && rightCol < map.count {
        if map[row1][rightCol] == 0 && map[row2][rightCol] == 0 {
            ret.append([row1, col, row1, rightCol,0])
            ret.append([row2, col, row2, rightCol,0])
        }
    }
    
    if leftCol >= 0 && leftCol < map.count {
        if map[row1][leftCol] == 0 && map[row2][leftCol] == 0 {
            ret.append([row1, leftCol, row1, col,0])
            ret.append([row2, leftCol, row2, col,0])
        }
    }
    

    
    return ret
}

struct Queue {
    private var leftStack: [[Int]] = []
    private var rightStack: [[Int]] = []
    
    mutating func append(_ a: [Int]) {
        rightStack.append(a)
    }
    
    public mutating func removeFirst() -> [Int] {
        if leftStack.isEmpty {
            leftStack = rightStack.reversed()
            rightStack.removeAll()
        }
        return leftStack.removeLast()
    }
    
    public var isEmpty: Bool {
        return rightStack.isEmpty && leftStack.isEmpty
    }
}
