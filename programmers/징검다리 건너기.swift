// 범위가 주어져 있으며 최대 또는 최솟값을 찾아야 한다면 binary search를 사용해 보자. 정답을 mid+1로 해야 하는 것을 눈치채지 못해 시간이 오래 걸렸다.

import Foundation

func solution(_ stones:[Int], _ k:Int) -> Int {
    let left = 0
    let right = 200000000
    var answer = 0
    found(left: left, right: right, stones: stones, k: k, answer: &answer)
    return answer+1
}

func found (left: Int, right: Int, stones: [Int], k: Int, answer: inout Int) {
    if left > right {
        return
    }
    let mid = (left + right)/2
    if check(stones: stones, k: k, mid: mid) {
        answer = max(answer, mid)
        found(left: mid+1, right: right, stones: stones, k: k, answer: &answer)
    } else {
        found(left: left, right: mid-1, stones: stones, k: k, answer: &answer)
    }
}

func check (stones: [Int], k: Int, mid: Int) -> Bool {
    var q = 0

    for i in 0..<stones.count {
        if stones[i]-mid <= 0 {
            q += 1
        } else {
            q = 0
        }
        
        if q >= k {
            return false
        }
    }
    
    return true
}
