// 효율성 통과를 못 할 줄 알았는데 의외였다. 애초에 2진법 표현시 1의 개수가 같아야 하기 때문에 값 변동 범위가 크지 않았던 것 같다.

import Foundation

func solution(_ n:Int) -> Int
{
    let n2jin = count(value: String(n, radix: 2))
    var i = 1
    
    while (true) {
        let num = n + i
        let num2jin = String(num,radix: 2)
        if count(value: num2jin) == n2jin {
            return num
        }
        i += 1
    }
}

func count (value: String) -> Int {
    var count: Int = 0
    for i in value {
        switch i {
        case "0":
            break
        case "1":
            count += 1
        default:
            break
        }
    }
    return count
}

