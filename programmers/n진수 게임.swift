func solution(_ n:Int, _ t:Int, _ m:Int, _ p:Int) -> String {
    var temp: [Character] = ["0"]
    var current = 0
    while temp.count < m*t {
        temp += Array(String(current, radix: n, uppercase: true))
        current += 1
    }
    
    var ret: [Character] = []
    for i in stride(from: p, through: (t-1)*m+p, by: m) {
        ret.append(temp[i])
    }

    return String(ret)
}
