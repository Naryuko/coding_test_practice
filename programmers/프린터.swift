import Foundation

func solution(_ priorities:[Int], _ location:Int) -> Int {
    var priorities = priorities
    
    // priorities의 index를 저장할 배열
    var indexList: [Int] = [Int] (repeating: 0, count: priorities.count)
    for i in 0..<indexList.count {
        indexList[i] = i
    }
    
    var index: Int = 0
    while index < indexList.count {
        let tempList: [Int] = Array(priorities[index+1..<priorities.count])
        if priorities[index] < tempList.max() ?? 0 {
            let tempPr: Int = priorities.remove(at: index)
            let tempIndex: Int = indexList.remove(at: index)
            priorities.append(tempPr)
            indexList.append(tempIndex)
        } else {
            index += 1
        }
    }
    return indexList.firstIndex(of: location)! + 1
}

