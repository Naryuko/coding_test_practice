// 정규표현식 연습

import Foundation

func solution(_ user_id:[String], _ banned_id:[String]) -> Int {
    var answerSet = Set<[String]>()

    let banIndexQueue = Queue<Int>()
    let checkQueue = Queue<[Bool]>()
    let banListQueue = Queue<[String]>()

    banIndexQueue.add(0)
    checkQueue.add([Bool] (repeating: false, count: user_id.count))
    banListQueue.add([])
    
    one: while !banIndexQueue.isEmpty() {
        let banIndex = banIndexQueue.poll()
        var check = checkQueue.poll()
        var banList = banListQueue.poll()
        
        if banIndex == banned_id.count {
            answerSet.insert(banList.sorted())
            continue one
        }
        
        let regex = "^" + banned_id[banIndex].replacingOccurrences(of: "*", with: ".") + "$"
        
        two: for i in 0..<user_id.count {
            if check[i] { continue }
            
            let currentUser = user_id[i]
            
            guard let _ = currentUser.range(of: regex, options: .regularExpression) else {
                continue two
            }
            
            banList.append(currentUser)
            check[i] = true
            
            banIndexQueue.add(banIndex + 1)
            checkQueue.add(check)
            banListQueue.add(banList)
            
            check[i] = false
            banList.removeLast()
            
        }
    }
    
    return answerSet.count
}

class Queue<T> {
    private var right: [T] = []
    private var left: [T] = []

    func add (_ item: T) {
        self.right.append(item)
    }

    func poll () -> T {
        if left.count == 0 {
            left = right.reversed()
            right.removeAll()
        }

        return left.removeLast()
    }

    func isEmpty () -> Bool {
        return left.count == 0 && right.count == 0
    }
}
