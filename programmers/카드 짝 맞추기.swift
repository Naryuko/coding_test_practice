// 단순히 배열로 구현하 큐는 정말 느리다. 최대한 사용 개수를 줄이자. 아니면 아래오 같이 stack 2개를 이용한 큐를 구현하자. 대략 1.5배 정도 빨라졌다.

import Foundation

let dRow = [-1, 1, 0, 0]
let dCol = [0, 0, -1, 1]

class Card {
    var firstRow: Int
    var firstCol: Int
    var secondRow: Int?
    var secondCol: Int?
    
    init (row: Int, col: Int) {
        self.firstRow = row
        self.firstCol = col
    }
    
    func second (row: Int, col: Int) {
        self.secondRow = row
        self.secondCol = col
    }
    
    func same (row: Int, col: Int) -> [Int] {
        if row == self.firstRow && col == self.firstCol {
            return [self.secondRow!, self.secondCol!]
        } else {
            return [self.firstRow, self.firstCol]
        }
    }
}


func solution(_ board:[[Int]], _ r:Int, _ c:Int) -> Int {
    var resume: Int = 0 // 남아있는 카드 수
    var answer = Int.max
    var witch: [Card?] = [Card?] (repeating: nil, count: 7)// i번째 칸에는 i번째 카드의 정보가 담긴다.
    var witchCheck: [Bool] = [Bool] (repeating: false, count: 7)
    
    
    for i in 0..<board.count {
        for j in 0..<board[i].count {
            if board[i][j] != 0 {
                resume += 1
                if !witchCheck[board[i][j]] {
                    witch[board[i][j]] = Card(row: i, col: j)
                    witchCheck[board[i][j]] = true
                } else {
                    witch[board[i][j]]!.second(row: i, col: j)
                }
            }
        }
    }
            
    var queue = Queue<[Int]>() // [row, col, 이동 횟수]
    var mapQueue = Queue<[[Int]]>()
    var witchCheckQueue = Queue<[Bool]>()
    
    queue.append([r, c, 0, resume])
    mapQueue.append(board)
    witchCheckQueue.append(witchCheck)
    
    while !queue.isEmpty {
        let temp = queue.removeFirst()
        var map = mapQueue.removeFirst()
        var wCheck = witchCheckQueue.removeFirst()
                
        if temp[3] == 0 {
            answer = min(answer, temp[2])
            continue
        } else if temp[2] >= answer {
            continue
        }
        
        if temp[0] < 0 || temp[1] < 0 || temp[0] >= 4 || temp[1] >= 4 {
            continue
        }
        
        if map[temp[0]][temp[1]] == 0 {
            let tt = searchCard(map: map, witchCheck: wCheck, witch: witch, row: temp[0], col: temp[1])
            for var m in tt {
                m[2] += temp[2]
                m.append(temp[3])
                
                queue.append(m)
                mapQueue.append(map)
                witchCheckQueue.append(wCheck)
            }
        } else {
            let cardNum = map[temp[0]][temp[1]]
            let same = searchSame(map: map, row: temp[0], col: temp[1], cardNum: cardNum, witch: witch)

            wCheck[cardNum] = false
            
            map[temp[0]][temp[1]] = 0
            map[same[0]][same[1]] = 0
            
            queue.append([same[0],same[1],same[2]+temp[2],temp[3]-2])
            mapQueue.append(map)
            witchCheckQueue.append(wCheck)
        }
        
        
    }
    return answer
}

func ccc (map: [[Int]], mapCheck: [[Bool]], row1: Int, col1: Int, row2: Int, col2: Int) -> Int {
    var answer: Int = 0
    
    var queue: [[Int]] = []
    var checkQueue: [[[Bool]]] = []
    
    queue.append([row1, col1, 0])
    checkQueue.append(mapCheck)
    
    while !queue.isEmpty {
        let temp = queue.removeFirst()
        var mCheck = checkQueue.removeFirst()
        
        if temp[0] == row2 && temp[1] == col2 {
            answer = temp[2]
            break
        }
        
        mCheck[temp[0]][temp[1]] = true
        
        for i in 0...3 {
            let dx = temp[0] + dRow[i]
            let dy = temp[1] + dCol[i]
            
            if dx < 0 || dy < 0 || dx >= 4 || dy >= 4 {
                continue
            }
            if !mCheck[dx][dy] {
                queue.append([dx, dy, temp[2]+1])
                checkQueue.append(mCheck)
            }
            
            var rowT = temp[0]
            var colT = temp[1]
            while true {
                let t1 = rowT + dRow[i]
                let t2 = colT + dCol[i]
                
                if t1 < 0 || t2 < 0 || t1 >= 4 || t2 >= 4 {
                    break
                }

                rowT = t1
                colT = t2
                if map[rowT][colT] != 0 {
                    break
                }
            }
            if rowT != dx || colT != dy && !mCheck[rowT][colT] {
                queue.append([rowT, colT, temp[2]+1])
                checkQueue.append(mCheck)
            }
        }
        
    }
    return answer
}

// 커서가 그림이 있는 칸일 경우 같은 카드가 있는 위치와 이동 횟수 반환 [row, col, 이동횟수]
func searchSame (map: [[Int]], row: Int, col: Int, cardNum: Int, witch: [Card?]) -> [Int] {
    var ret = [0,0,0]
    let temp = witch[cardNum]!.same(row: row, col: col)
    let mapCheck = [[Bool]] (repeating: [Bool] (repeating: false, count: 4), count: 4)

    ret[0] = temp[0]
    ret[1] = temp[1]
    ret[2] = ccc(map: map, mapCheck: mapCheck, row1: row, col1: col, row2: temp[0], col2: temp[1])
    ret[2] += 2

    return ret
}

// 커서가 빈 칸일 경우 카드가 있는 위치를 반환, [row, col, 도착하는데 필요한 입력 횟수]
func searchCard (map: [[Int]], witchCheck: [Bool], witch: [Card?], row: Int, col: Int) -> [[Int]] {
    var ret: [[Int]] = []
    let mapCheck = [[Bool]] (repeating: [Bool] (repeating: false, count: 4), count: 4)
    
    for i in 0..<witch.count {
        if let temp = witch[i] {
            if !witchCheck[i] {
                continue
            }
            let answer1 = ccc(map: map, mapCheck: mapCheck, row1: row, col1: col, row2: witch[i]!.firstRow, col2: witch[i]!.firstCol)
            let answer2 = ccc(map: map, mapCheck: mapCheck, row1: row, col1: col, row2: witch[i]!.secondRow!, col2: witch[i]!.secondCol!)
                        
            ret.append([temp.firstRow, temp.firstCol, answer1])
            ret.append([temp.secondRow!, temp.secondCol!, answer2])
        }
    }
    
    return ret
}

struct Queue<jen> {
    private var rightStack: [jen] = []
    private var leftStack: [jen] = []
    
    mutating func removeFirst() -> jen {
        if leftStack.isEmpty {
            leftStack = rightStack.reversed()
            rightStack.removeAll()
        }
        return leftStack.removeLast()
    }
    
    mutating func append (_ item: jen) {
        self.rightStack.append(item)
    }
    
    var isEmpty: Bool {
        return rightStack.isEmpty && leftStack.isEmpty
    }
}



