// timeout을 체크하지 않아서 매우 쉬운 문제였다. 시간을 체크했다면 먼저 tree를 만들고 dfs를 통해 했어야 했나?

import Foundation

func solution(_ enroll:[String], _ referral:[String], _ seller:[String], _ amount:[Int]) -> [Int] {
    var dict: [String:Int] = [:]
    let n = enroll.count
    var ret: [Int] = [Int] (repeating: 0, count: n)
    for i in 0..<enroll.count {
        dict[enroll[i]] = i
    }
    
    for i in 0..<amount.count {
        wow(dict: dict, seller: seller[i], money: amount[i]*100, ret: &ret, referral: referral)
    }
    
    
    return ret
}

func wow (dict: [String:Int], seller: String, money: Int, ret: inout [Int], referral: [String]) {
    let num = dict[seller]!
    let cost = money/10
    ret[num] += money - cost
    if referral[num] != "-" {
        wow(dict: dict, seller: referral[num], money: cost, ret: &ret, referral: referral)
    }
}
