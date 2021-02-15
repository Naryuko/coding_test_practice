import Foundation

func solution(_ progresses:[Int], _ speeds:[Int]) -> [Int] {
    // 각 progress별 작업 완료 날짜를 담는 배열
    var endList: [Int] = []
    
    for i in 0..<progresses.count {
        // 진행 상황 + j*작업 속도 값이 100 이상이 될 때 까지 j를 키운다
        var j: Int = 0
        while progresses[i] + j*speeds[i] < 100 {
            j += 1
        }
        // 해당 j를 endList에 담는다
        endList.append(j)
    }
    print(endList)
    
    // 정답을 저장할 배열
    var ret: [Int] = []
    
    var index: Int = 0 // endList의 index
    var num: Int = 0 // 현재 작업보다 시간이 오래 걸리는 이전 작업 수
    var current: Int = endList[0] // 현재 작업의 완료 시간
    while index < endList.count {
        if endList[index] <= current { // 현재 완료 시간이 직전 작업보다 더 짧으면
            num += 1                  // num을 1 상승
            index += 1
        } else {                      // 현재 완료 시간이 직전 작업보다 더 길면
            ret.append(num)          // num을 ret에 추가
            num = 0                   // num 초기화
            current = endList[index]
        }
    }
    ret.append(num)                   // 마지막 작업의 경우 다음 작업이 없기 때문에 위 루프에 포함되지 않으므로 최종 num도 포함시킨다
    
    return ret
}

