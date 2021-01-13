import Foundation

func solution(_ bridge_length:Int, _ weight:Int, _ truck_weights:[Int]) -> Int {
    // 현재 다리 위의 트럭 상태를 나타내는 배열. i번째 값은 i번째 트럭이 다리를 건널때까지 남은 시간을 뜻한다.
    // 값이 0일 경우 다리를 다 건넌 것 또는 다리에 아직 올라가지 않은 경우이다.
    var bridge: [Int] = [Int] (repeating: 0, count: truck_weights.count)
    var currentTruck: Int = 0 // 현재 트럭의 index(다음으로 다리에 올라갈 트럭의 index)
    var time: Int = 0
    
    while currentTruck < truck_weights.count || sum(bridge: bridge, truckWeights: truck_weights) != 0 {
        step(bridge: &bridge) // 1초 후의 다리 상태로 만듬
        let currentWeight: Int = sum(bridge: bridge, truckWeights: truck_weights) // 현재 다리 위의 무게
        // 다리 위에 다음 트럭이 올라가도 허용 무게 이하라면
        if currentTruck < truck_weights.count && currentWeight + truck_weights[currentTruck] <= weight {
            bridge[currentTruck] = bridge_length // 다음 트럭을 다리 위로 올라오게 한다
            currentTruck += 1
        }
        time += 1
    }
    return time
}

func step (bridge: inout [Int]) { // 1초 후의 list로 만드는 함수
    for i in 0..<bridge.count {
        if bridge[i] != 0 {
            bridge[i] -= 1
        }
    }
}

func sum (bridge: [Int], truckWeights: [Int]) -> Int { // bridge에 있는 무게의 합을 반환
    var sum: Int = 0
    for i in 0..<bridge.count {
        if bridge[i] != 0 {
            sum += truckWeights[i]
        }
    }
    
    return sum
}

