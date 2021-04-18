// 이렇게 구현해도 충분히 빠르다.
func solution(_ cacheSize:Int, _ cities:[String]) -> Int {
    var ret: Int = 0
    var cashe: [String] = []
    if cacheSize == 0 {
        return cities.count * 5
    }
    
    for i in 0..<cities.count {
        let value = cities[i].lowercased()
        if cashe.contains(value) {
            ret += 1
            cashe.append(cashe.remove(at: cashe.firstIndex(of: value)!))
        } else {
            ret += 5
            if cashe.count < cacheSize {
                cashe.append(value)
            } else {
                cashe.removeFirst()
                cashe.append(value)
            }
        }
    }
    
    return ret
}


// LRU class를 구현할 경우? 이 경우 모든 연산이 O(1)이라 함.
func solution(_ cacheSize:Int, _ cities:[String]) -> Int {
    var ret: Int = 0
    let cashe = LRU(capacity: cacheSize)
    if cacheSize == 0 {
        return cities.count * 5
    }
    
    for i in 0..<cities.count {
        let value = cities[i].lowercased()
        if let _ = cashe.get(value) {
            ret += 1
        } else {
            ret += 5
        }
        cashe.put(value: value)
    }
    
    return ret
}

class node {
    let value: String
    var next: node?
    var post: node?
    
    init (value: String) {
        self.value = value
    }
}

class LRU {
    let capacity: Int
    var map: [String:node]
    var head: node
    var tail: node
    
    init (capacity: Int) {
        self.capacity = capacity
        map = [:]
        head = node(value: "")
        tail = node(value: "")
        head.next = tail
        tail.post = head
    }
    
    func remove (node: node) {
        node.post!.next = node.next!
        node.next!.post! = node.post!
        map.removeValue(forKey: node.value)
    }
    
    func addToHead (node: node) {
        self.head.next!.post = node
        node.next = self.head.next!
        node.post = self.head
        self.head.next = node
        
        map[node.value] = node
    }
    
    func get (_ value: String) -> String? {
        if map[value] == nil {
            return nil
        }
        let getNode: node = map[value]!
        remove(node: getNode)
        addToHead(node: getNode)
        return getNode.value
    }
    
    func put (value: String) {
        let newNode: node = node(value: value)
        if map[newNode.value] != nil {
            let temp = map[newNode.value]!
            remove(node: temp)
        } else {
            if map.count >= self.capacity {
                let toDelete = tail.post!
                remove(node: toDelete)
            }
        }
        addToHead(node: newNode)
    }
}
