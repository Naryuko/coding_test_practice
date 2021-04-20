import Foundation

func solution(_ s:String) -> [Int] {
    var s = s
    var count: Int = 0
    var numberOfZero: Int = 0
    
    while (s != "1") {
        count += 1
        var temp: Int = 0
        (temp, s) = get(s: s)
        numberOfZero += temp
    }
    return [count, numberOfZero]
}

func get (s: String) -> (Int, String) {
    var count = 0
    var len = 0
    for char in s {
        if char == "0" {
            count += 1
        } else {
            len += 1
        }
    }
    
    return (count, String(len, radix: 2))
}
