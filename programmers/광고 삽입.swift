// 처음에는 각 log의 시작과 끝 시간을 구하고 로그마다 시작...끝을 돌며 일일이 timeStamp 값을 올렸다.
// log 수가 많아지니 timeStemp 순회 횟수가 너무 많아져서 시간 초과가 발생했다.
// 각 log별 시작 시간 +1, 끝나는 시간 -1만 적용 후 마지막 한번만 timeStemp[i] += timeStamp[i-1]로
// 누적 값을 구한 경우 반복 횟수가 현저하게 줄어 실행 시간이 최대 5배나 차이났다.
// 이 문제와 같이 이전 arr? 또는 단계?의 값을 더하는 식의 문제가 또 있었다.
// 이런 방법이 존재한다는 사실을 기억하자

import Foundation

func solution(_ play_time:String, _ adv_time:String, _ logs:[String]) -> String {
    let playTime = str2sec(play_time)[0]
    let advTime = str2sec(adv_time)[0]
    
    var timeStamp = [Int] (repeating: 0, count: playTime+1)
    
    for log in logs {
        let time = str2sec(log)
        timeStamp[time[0]] += 1
        timeStamp[time[1]] -= 1
    }
    
    for i in 1...playTime {
        timeStamp[i] += timeStamp[i-1]
     }
        
    var startTime = 0
    var endTime = advTime
    var temp = 0
    
    for i in 0..<endTime {
        temp += timeStamp[i]
    }
    
    var maxValue = temp
    var maxTime = 0
    
    while endTime <= playTime {
        temp -= timeStamp[startTime]
        temp += timeStamp[endTime]
        
        startTime += 1
        endTime += 1
        
        if maxValue < temp {
            maxValue = temp
            maxTime = startTime
        }
        
    }
    
    return sec2str(maxTime)
}

func sec2str (_ time: Int) -> String {
    let sec = time%60
    let remain = time/60
    let min = remain%60
    let hour = remain/60
    
    let Hour = hour < 10 ? "0"+String(hour) : String(hour)
    let Min = min < 10 ? "0"+String(min) : String(min)
    let Sec = sec < 10 ? "0"+String(sec) : String(sec)
    
    return Hour + ":" + Min + ":" + Sec
}

func str2sec (_ time: String) -> [Int] {
    let charArr = Array(time)
    var answer: [Int] = []
    
    if charArr.count == 8 {
        let hour = Int(String(charArr[0...1]))! * 60 * 60
        let min = Int(String(charArr[3...4]))! * 60
        let sec = Int(String(charArr[6...7]))!
        answer.append(hour + min + sec)
    } else {
        let hourS = Int(String(charArr[0...1]))! * 60 * 60
        let minS = Int(String(charArr[3...4]))! * 60
        let secS = Int(String(charArr[6...7]))!
        answer.append(hourS + minS + secS)
        
        let hourE = Int(String(charArr[9...10]))! * 60 * 60
        let minE = Int(String(charArr[12...13]))! * 60
        let secE = Int(String(charArr[15...16]))!
        answer.append(hourE + minE + secE)
    }
    
    return answer
}
