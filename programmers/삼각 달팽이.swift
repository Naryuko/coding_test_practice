// 약간 생각해야 하는 단순 구현문제였다. 문제를 보았을 때 단순 구현인지 규칙을 찾아야 하는 것인지 파악하는 능력을 기를 필요가 있어보인다.

import Foundation

func solution(_ n:Int) -> [Int] {
    var arr: [[Int]] = [[Int]] (repeating: [], count: n)
    for i in 0..<n {
        arr[i] = [Int] (repeating: 0, count: i+1)
    }
    var remain: Int = n*(n+1)/2
    var row = -1
    var col = 0
    var num = 1
    
    while remain != 0 {
        num = down(arr: &arr, row: &row, col: col, num: num, remain: &remain)
//        print("row: ",row," col: ",col," remain: ",remain)
        num = right(arr: &arr, row: row, col: &col, num: num, remain: &remain)
//        print("row: ",row," col: ",col," remain: ",remain)
        num = left(arr: &arr, row: &row, col: &col, num: num, remain: &remain)
//        print("row: ",row," col: ",col," remain: ",remain)
    }
    
    var ret: [Int] = []
    for i in 0..<arr.count {
        for j in 0..<arr[i].count {
            ret.append(arr[i][j])
        }
    }
    return ret
}

func down (arr: inout [[Int]], row: inout Int, col: Int, num: Int, remain: inout Int) -> Int {
    var num = num
    let roww = row
    for i in roww+1..<arr.count {
        if arr[i][col] != 0 { break }
        arr[i][col] = num
        num += 1
        remain -= 1
        row += 1
    }
    return num
}

func right (arr: inout [[Int]], row: Int, col: inout Int, num: Int, remain: inout Int) -> Int {
    var num = num
    let coll = col
    for i in coll+1..<arr[row].count {
        if arr[row][i] != 0 { break }
        arr[row][i] = num
        num += 1
        remain -= 1
        col += 1
    }
    return num
}

func left (arr: inout [[Int]], row: inout Int, col: inout Int, num: Int, remain: inout Int) -> Int {
    var num = num
    let roww = row
    let coll = col
    for i in 1..<arr.count {
        if arr[roww-i][coll-i] != 0 { break }
        arr[roww-i][coll-i] = num
        num += 1
        remain -= 1
        row -= 1
        col -= 1
    }
    
    return num
}
