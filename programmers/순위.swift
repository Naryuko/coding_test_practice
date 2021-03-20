import Foundation

func solution(_ n:Int, _ results:[[Int]]) -> Int {
    var winList: [[Int]] = [[Int]] (repeating: [], count: n+1) // 자신을 이긴 사람
    var loseList: [[Int]] = [[Int]] (repeating: [], count: n+1) // 자신에게 진 사람
    
    for result in results {
        let win: Int = result[0]
        let lose: Int = result[1]
        winList[win].append(lose)
        loseList[lose].append(win)
    }
    
    for i in 0...n {
        let win: [Int] = winList[i] // 나를 이긴 사람
        let lose: [Int] = loseList[i] // 나한테 진 사람
        
        for winner in win {
            for loser in lose {
                if !winList[loser].contains(winner) {
                    winList[loser].append(winner)
                }
                if !loseList[winner].contains(loser) {
                    loseList[winner].append(loser)
                }
            }
        }
    }
    print(winList)
    print(loseList)
    
    var ret: Int = 0;
    for i in 0...n {
        if winList[i].count + loseList[i].count == n-1 {
            ret += 1
        }
    }
    return ret
}
