import Foundation

func solution(_ clothes:[[String]]) -> Int {
    // [의상의 종류:그 종류의 의상 수] 형식의 정보를 저장할 딕셔너리를 만든다.
    var clothesDict: [String:Int] = [:]
    
    // 만든 딕셔너리에 정보를 저장한다.
    for cloth in clothes{
        if clothesDict[cloth[1]] == nil {
            clothesDict[cloth[1]] = 1
        } else {
            clothesDict[cloth[1]]! += 1
        }
    }
    
    var sum: Int = 1
    for (_, value) in clothesDict {
        sum *= (value + 1)
    }
    
    return sum - 1
}
