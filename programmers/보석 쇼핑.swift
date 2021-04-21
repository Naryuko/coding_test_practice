import Foundation

func solution(_ gems:[String]) -> [Int] {
    var set: Set<String> = []
    for i in gems {
        set.insert(i)
    }
    let kind: Int = set.count
    var dict: [String:Int] = [gems[0]:1]
    var leftIndex: Int = 0
    var rightIndex: Int = 0
    var leftFinal: Int = 0
    var rightFinal: Int = Int.max
    
    while rightIndex < gems.count && leftIndex < gems.count {
//        print("left: ",leftIndex," right: ",rightIndex, " dict: ",dict)
        if (dict.count < kind) {
            rightIndex += 1
            if rightIndex < gems.count {
                if dict[gems[rightIndex]] == nil {
                    dict[gems[rightIndex]] = 1
                } else {
                    dict[gems[rightIndex]]! += 1
                }
            }
        } else if (dict.count == kind) {
            if rightIndex-leftIndex < rightFinal-leftFinal {
                rightFinal = rightIndex
                leftFinal = leftIndex
            } else if rightIndex-leftIndex == rightFinal-leftFinal && leftIndex < leftFinal {
                leftFinal = leftIndex
                rightFinal = rightIndex
            }
            
            if dict[gems[leftIndex]] == 1 {
                dict.removeValue(forKey: gems[leftIndex])
            } else {
                dict[gems[leftIndex]]! -= 1
            }
            if leftIndex+1 < gems.count {
                leftIndex += 1
            }
            
        }
    }
    
    return [leftFinal+1, rightFinal+1]
}
