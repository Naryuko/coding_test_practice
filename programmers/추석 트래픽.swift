// Double로 풀 시 역시 오류가 발생했다. Int형을 바꿔서 풀어야 한다 역시.

import Foundation

func solution(_ lines:[String]) -> Int {
    var answer = 0
    
    var timeToCheck: [Int] = []
    var timeDict: [Int:Bool] = [:]
    var trafficList: [[Int]] = []

    for line in lines {
        let hourStartS = line.index(line.startIndex, offsetBy: 11)
        let hourEndS = line.index(line.startIndex, offsetBy: 12)
        let minStartS = line.index(line.startIndex, offsetBy: 14)
        let minEndS = line.index(line.startIndex, offsetBy: 15)
        let secStartS = line.index(line.startIndex, offsetBy: 17)
        let secEndS = line.index(line.startIndex, offsetBy: 22)

        let firstIndexT = line.index(line.startIndex, offsetBy: 24)
        let lastIndexT = line.index(line.endIndex, offsetBy: -2)

        let hour = (Int(line[hourStartS...hourEndS]) ?? 0 )*60*60*1000
        let min = (Int(line[minStartS...minEndS]) ?? 0)*60*1000
        let sec = Int((Double(line[secStartS...secEndS]) ?? 0)*1000)

        let T = Int((Double(line[firstIndexT...lastIndexT]) ?? 0)*1000)
        
        let endTime = hour + min + sec
        let startTime = endTime - T + 1
                
        if timeDict[endTime] == nil {
            timeDict[endTime] = true
            timeToCheck.append(endTime)
        }
        if timeDict[startTime] == nil {
            timeDict[startTime] = true
            timeToCheck.append(startTime)
        }
        trafficList.append([startTime, endTime])
        
    }
    
    for time in timeToCheck {
        var temp = 0
        
        let start = time
        let end = time + 999
                
        for t in trafficList {
            if start > t[1] || end < t[0] {
                continue
            }
            temp += 1
        }
        answer = max(answer, temp)
    }
    
    return answer
}
