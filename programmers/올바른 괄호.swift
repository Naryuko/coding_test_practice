// 1. 문자열 인덱싱으로 접근 -> 매우 느리다.
// 2. let arr = Array(String)으로 char array로 만든다 -> 하나하나 접근할 때는 빠르지만 결국 arr를 만드는 비용이 든다.
// 3. 단순히 string 안의 모든 것을 한 번씩 접근해야한다면 for i in String 을 사용하자. String을 구성하는 각각의 char에 대해 iter를 수행한다.

import Foundation

func solution(_ s:String) -> Bool
{
    var count: Int = 0
    var ans:Bool = true
    
    for i in s {
        switch i {
        case "(":
            count += 1
        case ")":
            count -= 1
        default:
            count += 1
        }
        
        if count < 0 {
            ans = false
            break
        }
    }
    
    if count != 0 {
        ans = false
    }

    return ans
}
