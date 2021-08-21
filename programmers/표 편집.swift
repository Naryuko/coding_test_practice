// erase. restore 할 때 아래 node만 확인했지만, 위 노드가 nil인 경우(가장 위 노드일 경우)를 깜빡해 core dump가 발생했다.
// 모든 에러 경우의 수 확이 잘 하자!
// 추가로 nil 처리 더 멋지게 할 방법도 생각해 보자.

import Foundation

func solution(_ n:Int, _ k:Int, _ cmd:[String]) -> String {
    let list = List(n: n, k: k)

    for command in cmd {
        let cmd = command[command.startIndex]

        switch cmd {
        case "U":
            list.up(for: Int(command.split(separator: " ")[1])!)
        case "D":
            list.down(for: Int(command.split(separator: " ")[1])!)
        case "C":
            list.erase
        case "Z":
            list.restore
        default:
            print(1)
        }

    }
    
    var answer: [Character] = [Character] (repeating: "O", count: n)
    
    for i in 0..<n {
        if list.eraseNodeDict[i] != nil {
            answer[i] = "X"
        }
    }

    return String(answer)
}

class List {
    var nodeDict: [Int:Node] = [:]
    var currentNode: Node
    var eraseNodeList: [Int] = []
    var eraseNodeDict: [Int:Bool] = [:]

    init (n: Int, k: Int) {
        for i in 0..<n {
            let tempNode = Node(i)
            nodeDict[i] = tempNode

            if i != 0 {
                nodeDict[i-1]!.downNode = tempNode
                tempNode.upNode = nodeDict[i-1]
            }

        }

        currentNode = nodeDict[k]!
    }

    func up (for num: Int) {
        for _ in 0..<num {
            currentNode = currentNode.upNode ?? currentNode
        }
    }

    func down (for num: Int) {
        for _ in 0..<num {
            currentNode = currentNode.downNode ?? currentNode
        }
    }

    var erase: () {
        let eraseNodenum = currentNode.num
        eraseNodeList.append(eraseNodenum)
        eraseNodeDict[eraseNodenum] = true

        if currentNode.downNode == nil {
            currentNode = currentNode.upNode!
            currentNode.downNode = nil
        } else if currentNode.upNode == nil {
            currentNode = currentNode.downNode!
            currentNode.upNode = nil
        } else {
            currentNode.upNode!.downNode = currentNode.downNode!
            currentNode.downNode!.upNode = currentNode.upNode!
            currentNode = currentNode.downNode!
        }
    }

    var restore: () {
        let restoreNum = eraseNodeList.removeLast()
        eraseNodeDict[restoreNum] = nil

        let tempNode = nodeDict[restoreNum]!
        if tempNode.upNode != nil {
            tempNode.upNode!.downNode = tempNode
        }
        if tempNode.downNode != nil {
            tempNode.downNode!.upNode = tempNode
        }
    }

    class Node {
        var upNode: Node?
        var downNode: Node?
        var num: Int

        init (_ num: Int) {
            self.num = num
        }
    }


}
