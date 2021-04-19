// subArray를 만드는 것으 시간이 매우 오래걸리므로 하지말자.
import Foundation

func solution(_ number:String, _ k:Int) -> String {
    var ret: [Character] = []
    let list = Array(number)
    let count = list.count
    var remain = count - k
    var start = 0
    
    while remain > 0 {
//        print("start: ",start," end: ",count-remain, " ret: ",ret)
//        let temp = Array(list[start...count - remain])
        let (index, val) = findMax(a: list, start: start, end: count - remain)
        start = index + 1
        ret.append(val)
        remain -= 1
    }
    
    return String(ret)
}

func findMax (a: [Character], start: Int, end: Int) -> (Int, Character) {
    var maxx: Character = "-"
    var index = 0
    
    for i in start...end {
        if a[i] == "9" {
            maxx = "9"
            index = i
            break
        }
        if maxx < a[i] {
            maxx = a[i]
            index = i
        }
    }
    
    return (index, maxx)
}

// String을 python과 같이 인덱싱으로 접근하는 방법
extension String {
    subscript (value: Int) -> Character {
        return self[self.index(self.startIndex, offsetBy: value)]
    }
}

