// dfs시 검사를 dfs 함수에 넘겨준 다음 실행하면 시간이 너무 오래걸린다.
// check 배열을 사용할 경우 매 dfs마다 새로 var check = check로 할당하는 경우도
// 시간을 오래 잡아먹었다. inout을 통해 reference로 접근하자.

import Foundation

func solution(_ a:[Int], _ edges:[[Int]]) -> Int64 {
    var tree: [[Int]] = [[Int]] (repeating: [], count: a.count)
    var answer: Int64 = 0
    var a = a
    var check = [Bool] (repeating: false, count: a.count)
    
    for edge in edges {
        tree[edge[0]].append(edge[1])
        tree[edge[1]].append(edge[0])
    }
    
    dfs(tree: tree, a: &a, current: 0, parent: -1, check: &check, answer: &answer)
    
    for i in a {
        if i != 0 {
            answer = -1
            break
        }
    }
    
    return answer
}

func dfs (tree: [[Int]], a: inout [Int], current: Int, parent: Int, check: inout [Bool], answer: inout Int64) {
    if check[current] { return }
    check[current] = true
    
    for next in tree[current] {
        if !check[next] {
            dfs(tree: tree, a: &a, current: next, parent: current, check: &check, answer: &answer)
        }
    }
    
    answer += Int64(abs(a[current]))
    if parent != -1 {
        a[parent] += a[current]
        a[current] = 0
    }
}
