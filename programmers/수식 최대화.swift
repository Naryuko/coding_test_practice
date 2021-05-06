// regular expression 사용법을 터득한 것 같다. 총 문자열 길이가 짧아 최적화 걱정을 하지 않고 생각나는대로 구현하면 되었다.

import Foundation

func solution(_ expression:String) -> Int64 {
    let endIndex = expression.endIndex
    
    let regex = try! NSRegularExpression(pattern: "[0-9]+", options: [])
    let matches = regex.matches(in: expression, options: [], range: NSRange(location: 0, length: expression.count))
    var numList: [Int64] = []
    var cal: [String] = []
    
    for i in 0..<matches.count {
        let range = Range(matches[i].range, in: expression)!
        numList.append(Int64(String(expression[range]))!)
        if range.upperBound < endIndex {
            let c = String(expression[range.upperBound])
            cal.append(c)
        }
    }
    
    
    return fs(num: numList, cal: cal)
}

func fs (num: [Int64], cal: [String]) -> Int64 {
    var ret: Int64 = 0
    let orders = [["+","-","*"],
                 ["+","*","-"],
                 ["-","+","*"],
                 ["-","*","+"],
                 ["*","+","-"],
                 ["*","-","+"]]
    
    for order in orders {
        var num2 = num
        var cal2 = cal
        for i in 0...2 {
            let operatorr = order[i]
            var j = 0
            while j < cal2.count {
                if cal2[j] == operatorr {
//                    print("oper: ",operatorr," first: ",num2[j]," second: ",num2[j+1]," j: ",j," cal: ",cal2," num2: ",num2)
                    num2[j] = calculate(oper: operatorr, first: num2[j], second: num2[j+1])
                    cal2.remove(at: j)
                    num2.remove(at: j+1)
                } else {
                    j += 1
                }
            }
        }
        
        ret = max(ret, abs(num2[0]))
        
    }
    
    
    return ret
}

func calculate (oper: String, first: Int64, second: Int64) -> Int64 {
    switch oper {
    case "+":
        return first+second
    case "-":
        return first-second
    case "*":
        return first*second
    default:
        return first
    }
}
