import Foundation

func solution(_ n:Int, _ times:[Int]) -> Int64 {
    let times = times.sorted(by: <)
    let answer = search(n: n, times: times)
    return answer
}

func search (n: Int, times: [Int]) -> Int64 {
    var min: Int64 = 1
    var max: Int64 = Int64(times.last!*n)
    var answer: Int64 = Int64.max
    
    while (min <= max) {
        let mid = (min+max)/2
        if check(n: n, times: times, mid: mid) {
            answer = mid > answer ? answer : mid
            max = mid - 1
        } else {
            min = mid + 1
        }
    }
    
    return answer
}

func check (n: Int, times: [Int], mid: Int64) -> Bool {
    var howMany: Int64 = 0
    
    for time in times {
        howMany += mid/Int64(time)
    }
    
    if howMany >= n {
        return true
    }
    return false
}

