import Foundation

// 현재 진행중인 작업의 종료시간보다 시작시간이 빠른 job들 중에서 소요시간이 짧은 작업 순으로 진행하면 된다.
func solution(_ jobs:[[Int]]) -> Int {
    var waiting: [[Int]] = [] // 현재 작업하고 있는 job의 종료시간보다 시작시간이 빠른 job들을 저장
    let jobs = jobs.sorted { $0[0] < $1[0] } // 시작시간이 작은 순서대로 정렬
    
    var current: Int = jobs[0][0] // 현재 시간, 처음에는 시작 시간, 이후부터는 수행한 job이 끝나는 시간
    waiting.append(jobs[0])
    var sum: Int = 0 // 총 작업시간의 합
    var count: Int = 1 // waiting 리스트에 들어가지 않은 가장 작은 jobs의 index
    
    while count < jobs.count || waiting.count != 0 {
        if count < jobs.count {
            for i in count..<jobs.count {
                if jobs[i][0] > current { // 작업의 시작시간이 현재 job 완료시간보다 크다면 빠져나간다
                    break
                }
                count += 1 // 아니라면 count를 증가시키고 해당 작업을 waiting list에 포함시킨다
                waiting.append(jobs[i])
            }
        }
        
        //만약 waiting list가 비어있다면 현재 시간을 1초 증가시키고 위 과정을 반복해 list를 채운다.
        if waiting.count == 0 {
            current += 1
            continue
        }
        
        // 여기까지 수행시 현재 job의 완료시간보다 시작시간이 빠른 job들이 waiting list에 들어오게 된다.
        waiting.sort { $0[1] < $1[1] } // 작업소요시간이 짧은 순서로 정렬한다.
        sum = sum + current - waiting[0][0] + waiting[0][1] // 요청부터 끝날때까지 시간 = (작업시작시간 - 요청시간) + 작업소요시간
        current = current + waiting[0][1] // 작업 완료시간을 다음 작업(waiting에 들어있는 첫번째 job)의 소요시간만큼 증가
        waiting.removeFirst() // waiting의 첫 번째 job을 수행 완료했으므로 waiting list에서 지워준다.
    }
    
    return sum / jobs.count
}
