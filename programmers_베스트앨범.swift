import Foundation

func solution(_ genres:[String], _ plays:[Int]) -> [Int] {
    // [장르:해당 장르의 총 재생 수] 형식의 딕셔너리
    var dict1: [String:Int] = [:]
    // [장르:[해당 장르에 속한 곡의 index 배열]] 형식의 딕셔너리
    var dict2: [String:[Int]] = [:]
    
    // genres, plays 정보를 dict1, dict2에 저장한다.
    for i in 0..<genres.count {
        if dict1[genres[i]] == nil {
            dict1[genres[i]] = plays[i]
            dict2[genres[i]] = [i]
        } else {
            dict1[genres[i]]! += plays[i]
            dict2[genres[i]]!.append(i)
        }
    }
    
    //장르 순서를 담는 배열
    var genresList: [String] = []
    for (key, _) in dict1 {
        genresList.append(key)
    }
    genresList.sort { dict1[$0]! > dict1[$1]! }
    
    //정답을 담을 배열
    var ret: [Int] = []
    for i in 0..<genresList.count {
        let genre: String = genresList[i] // 장르
        var j: Int = 0
        var tempList = dict2[genre]! // 해당 장르에 속한 노래의 index 배열
        // 위 tempList 배열을 재생횟수가 많으면 앞으로 오게, 재생횟수가 같으면 index가 작은 것이 더 앞으로 오게 정렬
        tempList.sort { (a: Int, b: Int) -> Bool in
            if plays[a] != plays[b] {
                return plays[a] > plays[b]
            } else {
                return a < b
            }
        }
        
        // tempList에 속한 원소가 2개 미만이라면 모두, 2개 이상이라면 2개까지만 순서대로 ret 배열에 담는다
        while j < 2 && j < tempList.count {
            ret.append(tempList[j])
            j += 1
        }
    }
    
    return ret
}
