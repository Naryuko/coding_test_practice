import Foundation

func solution(_ key:[[Int]], _ lock:[[Int]]) -> Bool {
    let M = key.count
    let N = lock.count
    var key = key
    var ret: Bool = false
    
    var rowStart: Int = 1 - M
    var rowEnd: Int = 0
    var colStart: Int = 1 - M
    var colEnd: Int = 0
    
    while (rowStart < N && colStart < N) {
        for _ in 0...3 {
            var ccc: Bool = false // 돌기가 만나면 true
            var temp = lock
            
            for i in rowStart...rowEnd {
                if i < 0 || i >= N {
                    continue
                }
                
                for j in colStart...colEnd {
                    if j < 0 || j >= N {
                        continue
                    }
                    let row = i - rowStart
                    let col = j - colStart
                    if key[row][col] == 1 && temp[i][j] == 1 {
                        ccc = true
                        break
                    }
                    if key[row][col] == 1 {
                        temp[i][j] = 1
                    }
                }
                if ccc {
                    break
                }
            }
            if check(map: temp) {
                ret = true
                break
            }
            rotate(key: &key, num: 0)
        }

        rowEnd = colStart + 1 >= N ? rowEnd + 1 : rowEnd
        rowStart = colStart + 1 >= N ? rowStart + 1 : rowStart
        colEnd = colStart + 1 >= N ? 0 : colEnd + 1
        colStart = colStart + 1 >= N ? 1 - M : colStart + 1
    }
    
    return ret
}

func check (map: [[Int]]) -> Bool {
    for i in map {
        for j in i {
            if j != 1 {
                return false
            }
        }
    }
    
    return true
}


func rotate (key: inout [[Int]], num: Int) {
    let start: Int = num
    let end: Int = key.count - 1 - num
    if start >= end {
        return
    }
    
    var temp: [Int] = [Int] (repeating: -1, count: end - start + 1)
    
    // 1 -> 2
    for i in start..<end {
        temp[i - start] = key[i][end]
        key[i][end] = key[start][i]
    }
    
    // 2 -> 3
    for i in start..<end {
        let t: Int = temp[i - start]
        temp[i - start] = key[end][end - i + start]
        key[end][end - i + start] = t
    }

    // 3 -> 4
    for i in start..<end {
        let t: Int = temp[i - start]
        temp[i - start] = key[end - i + start][start]
        key[end - i + start][start] = t
    }

    // 4 -> 1
    for i in start..<end {
        key[start][i] = temp[i-start]
    }
    
    rotate(key: &key, num: num+1)
}
