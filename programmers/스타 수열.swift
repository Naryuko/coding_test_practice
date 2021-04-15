import Foundation

func solution(_ a:[Int]) -> Int {
    var dict: [Int:[Int]] = [:]
    var ret: Int = 0
    
    if a.count == 1 {
        return 0
    } else if a.count == 2 {
        return 1
    }
    
    for i in 0..<a.count {
        if dict[a[i]] != nil {
            if i == 0 {
                if a[i] == a[i+1] {
                    continue
                }
            } else if i == a.count-1 {
                if a[i] == a[i-1] {
                    continue
                }
            } else if a[i] == a[i-1] && a[i] == a[i+1] {
                continue
            }
            dict[a[i]]?.append(i)
        } else {
            dict[a[i]] = [i]
        }
    }
    
    var maxx = 0
    var maxValue: [Int] = []
    for i in dict.keys {
        let temp = dict[i]!.count
        if temp > maxx {
            maxx = temp
            maxValue.removeAll()
            maxValue.append(i)
        } else if temp == maxx {
            maxValue.append(i)
        }
    }
    
    
    for value in maxValue {
        let valueList = dict[value] ?? []
        var ans = 0
        var check: [Bool] = [Bool] (repeating: false, count: a.count)
        for i in stride(from: valueList.count-1, through: 0, by: -1) {
            let index = valueList[i]
            
            if index == a.count-1 {
                ans += 1
                check[index] = true
                check[index-1] = true
                continue
            }
            
            if index == 0 && !check[index+1] {
                ans += 1
                continue
            } else if index == 0 { continue }
                        
            if !check[index+1] && a[index] != a[index+1] {
                ans += 1
                check[index] = true
                check[index+1] = true
            } else if !check[index-1] && a[index] != a[index-1] {
                ans += 1
                check[index] = true
                check[index-1] = true
            }
            
        }
        
        ret = max(ret, ans)
    }
    
    return ret*2
}
