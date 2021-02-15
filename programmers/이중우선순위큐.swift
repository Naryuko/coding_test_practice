import Foundation

func solution(_ operations:[String]) -> [Int] {
    var queue: [Int] = [] // 주어진 명령을 처리해 숫자를 입력, 삭제할 배열
    
    // operation이 명하는 대로 차례차례 작업을 수행한다.
    for i in 0..<operations.count {
        let oper: String = operations[i]
        let first: Character = oper[oper.startIndex]
        let thirdIndex = oper.index(oper.startIndex, offsetBy: 2)
        let lastIndex = oper.index(before: oper.endIndex)
        if first == "I" {
            let num: Int = Int(oper[thirdIndex...lastIndex]) ?? 0
            queue.append(num)
            queue.sort(by: <)
        } else {
            if queue.count == 0 { // queue가 비어있으면 최대, 최솟값 삭제 연산을 수행하지 않는다
                continue
            }
            if oper[thirdIndex] == "-" {
                queue.removeFirst()
            } else {
                queue.removeLast()
            }
        }
    }
    var ret: [Int] = [0,0]
    if queue.count != 0 {
        ret[0] = queue[queue.count-1]
        ret[1] = queue[0]
        
    }
    return ret
}

