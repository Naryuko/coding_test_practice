import Foundation

func solution(_ N:Int, _ number:Int) -> Int {
    var ret: Int = 0
    var canMake: [[Int]] = [[Int]] (repeating: [], count: 9)
    canMake[1].append(N)
    if number == N {
        return 1
    }
    
    for i in 2...8 {
        let newV: Int = makeNum(N: N, value: N, iter: i)
        for j in 1...i/2+1 {
            for valueS in canMake[j] {
                for value in canMake[i-j] {
                    let plusV: Int = value + valueS
                    let minV: Int = value - valueS
                    let mulV: Int = value*valueS
                    let divV: Int = valueS == 0 ? 0:value/valueS
                    if plusV == number || minV == number || mulV == number || divV == number || newV == number {
                        ret = i
                        break
                    }
                    canMake[i].append(plusV)
                    canMake[i].append(minV)
                    canMake[i].append(mulV)
                    canMake[i].append(divV)
                }
                
                if ret != 0 {
                    break
                }
            }
            canMake[i].append(newV)
            if ret != 0 {
                break
            }
        }
        if ret != 0 {
            break
        }
    }
    if ret > 8 || ret == 0 {
        ret = -1
    }
    return ret
}

func makeNum (N: Int, value: Int, iter: Int) -> Int {
    if iter == 1 {
        return value
    }
    
    return makeNum(N: N, value: value*10 + N, iter: iter-1)
}
