// Int의 범위를 넘는 숫자의 공간을 가지는 배열을 만들면 런타임 에러가 발생할 수 있다. 이럴 때는 dict를 이용하자. 
// 처음에 바로 union find를 생각하지 못하고 class Room을 선언해 포인터로 연결하려 했었다. union-find와 tree 모두 연결 관계를 나타내기 때문에 무엇을 사용해야 할 지 잘 생각해 보자.

import Foundation

func solution(_ k:Int64, _ room_number:[Int64]) -> [Int64] {
    var dict: [Int64:Int64] = [:]
    var answer: [Int64] = []
    
    for num in room_number {
        if dict[num] == nil {
            unionParent(dict: &dict, a: num, b: num+1)
            answer.append(num)
        } else {
            let nn = findParent(dict: &dict, a: num)
            unionParent(dict: &dict, a: nn, b: nn+1)
            answer.append(nn)
        }
    }
    return answer
}

func findParent (dict: inout [Int64:Int64], a: Int64) -> Int64 {
    if dict[a] == nil {
        return a
    }
    
    dict[a] = findParent(dict: &dict, a: dict[a]!)
    return dict[a]!
}

func unionParent (dict: inout [Int64:Int64], a: Int64, b: Int64) {
    let aa = findParent(dict: &dict, a: a)
    let bb = findParent(dict: &dict, a: b)
    
    if aa < bb {
        dict[aa] = bb
    } else {
        dict[bb] = aa
    }
}
