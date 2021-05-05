// 위상 정렬로 풀 시 time out이 발생했다. 정점의 개수가 20만개인 만큼 1번 초과의 탐색을 수행할 경우 시간초과가 날 수 있었다.

import Foundation

import Foundation

func solution(_ n:Int, _ path:[[Int]], _ order:[[Int]]) -> Bool {
    var list = [[Int]] (repeating: [], count: n)
    for p in path {
        let from = p[0]
        let to = p[1]
        list[from].append(to)
        list[to].append(from)
    }
    
    var requiredVisit = [Int] (repeating: -1, count: n)
    for or in order {
        requiredVisit[or[1]] = or[0]
    }
    
    if requiredVisit[0] != -1 {
        return false
    }
    
    return bfs(n: n, list: list, req: requiredVisit)
}

func bfs (n: Int, list: [[Int]], req: [Int]) -> Bool {
    var queue = Queue<Int>()
    var check: [Bool] = [Bool] (repeating: false, count: n)
    var next: [Int] = [Int] (repeating: -1, count: n)
    
    queue.add(0)
    check[0] = true
    
    while !queue.isEmpty() {
        let current = queue.poll()
        
        for i in list[current] {
            if !check[i] && (req[i] == -1 || check[req[i]]) {
                queue.add(i)
                check[i] = true
            } else if req[i] != -1 {
                next[req[i]] = i
            }
        }
        
        if next[current] != -1 {
            check[next[current]] = true
            queue.add(next[current])
        }
        
    }
    
    for visit in check {
        if !visit {
            return false
        }
    }
    
    return true
}

// 위상정렬로 풀 시
//func solution(_ n:Int, _ path:[[Int]], _ order:[[Int]]) -> Bool {
//    var list: [[Int]] = [[Int]] (repeating: [], count: n)
//    var enterCounter: [Int] = [Int] (repeating: 0, count: n)
//    for p in path {
//        let from = p[0]
//        let to = p[1]
//        list[from].append(to)
//        list[to].append(from)
//    }
//
//    bfs(n: n, list: list, enterCounter: &enterCounter)
//
//    for p in order {
//        enterCounter[p[1]] += 1
//    }
//
//    if enterCounter[0] != 0 {
//        return false
//    }
//
//    return check(n: n, list: list, enterCounter: enterCounter, order: order)
//}


//func bfs (n: Int, list: [[Int]], enterCounter: inout [Int]) {
//    var queue = Queue<Int>()
//    var check: [Bool] = [Bool] (repeating: false, count: n)
//    queue.add(0)
//    check[0] = true
//
//    while !queue.isEmpty() {
//        let current = queue.poll()
//        for i in list[current] {
//            if !check[i] {
//                queue.add(i)
//                enterCounter[i] += 1
//                check[i] = true
//            }
//        }
//    }
//
//}
//
//func check (n: Int, list: [[Int]], enterCounter: [Int], order: [[Int]]) -> Bool {
//    var enterCounter = enterCounter
//    var hasVisited: [Bool] = [Bool] (repeating: false, count: n)
//
//    var queue = Queue<Int>()
//    var ret = true
//
//    queue.add(0)
//    hasVisited[0] = true
//
//    for _ in 0..<n {
//        if queue.isEmpty() {
//            ret = false
//            break
//        }
//
//        let current = queue.poll()
//
//        for or in order {
//            if or[0] == current {
//                enterCounter[or[1]] -= 1
//                if enterCounter[or[1]] == 0 && !hasVisited[or[1]] {
//                    hasVisited[or[1]] = true
//                    queue.add(or[1])
//                }
//            }
//        }
//
//        for i in list[current] {
//            enterCounter[i] -= 1
//            if enterCounter[i] == 0 && !hasVisited[i] {
//                hasVisited[i] = true
//                queue.add(i)
//            }
//        }
//
//
//    }
//
//    return ret
//}
//
//func checkVisit (hasVisited: [Bool]) -> Bool {
//    for visited in hasVisited {
//        if !visited { return false }
//    }
//    return true
//}


struct Queue<T> {
    private var left: [T] = []
    private var right: [T] = []
    
    mutating func add (_ value: T) {
        self.right.append(value)
    }
    
    mutating func poll () -> T {
        if left.count == 0 {
            left = right.reversed()
            right.removeAll()
        }
        return left.removeLast()
    }
    
    func isEmpty() -> Bool {
        return left.count == 0 && right.count == 0
    }
    
}
