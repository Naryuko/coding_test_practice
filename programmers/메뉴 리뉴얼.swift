import Foundation

func solution(_ orders:[String], _ course:[Int]) -> [String] {
    var orderList: [[String:Int]] = [[String:Int]] (repeating: [:], count: 26)
    
    for i in 0..<orders.count {
        let temp: [Character] = Array(orders[i]).sorted(by: <)
        for j in 1...temp.count {
            var ret: [String] = []
            make(ret: &ret, rett: "", count: j, current: 0, orderList: temp)
            for k in ret {
                if orderList[j][k] != nil {
                    orderList[j][k]! += 1
                } else {
                    orderList[j][k] = 1
                }
            }
        }
    }
        
    var ret: [String] = []
    
    for i in course {
        var maxValue: Int = 0
        var maxString: [String] = []
        for (key, value) in orderList[i] {
            if value > maxValue {
                maxValue = value
                maxString.removeAll()
                maxString.append(key)
            } else if value == maxValue {
                maxString.append(key)
            }
        }
        
        if maxValue < 2 {
            continue
        }
        for i in maxString {
            ret.append(i)
        }
    }
    
    return ret.sorted(by: <)
}

func make (ret: inout [String], rett: String, count: Int, current: Int, orderList: [Character]) {
    if count == 0 {
        ret.append(rett)
        return
    }
    
    for i in current..<orderList.count {
        make(ret: &ret, rett: rett+String(orderList[i]), count: count-1, current: i+1, orderList: orderList)
    }
    
}
