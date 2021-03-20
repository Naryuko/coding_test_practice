import Foundation

func solution(_ a:[Int]) -> Int {
    var ret:Int = 0
    var left: Int = Int.max
    var right: Int = Int.max
    
    var leftList: [Int] = [Int] (repeating: 0, count: a.count)
    var rightList: [Int] = [Int] (repeating: 0, count: a.count)
    
    for i in 0..<a.count {
        let leftValue: Int = a[i]
        let rightValue: Int = a[a.count-1-i]
        leftList[i] = left
        rightList[a.count-1-i] = right
        if left > leftValue {
            left = leftValue
        }
        if right > rightValue {
            right = rightValue
        }
    }
    
    for i in 0..<a.count {
        if a[i] > leftList[i] && a[i] > rightList[i] {
            continue
        }
        ret += 1
    }
    
    return ret
}
