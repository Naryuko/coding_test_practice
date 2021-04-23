import java.util.HashMap;

class Solution {
    public int[] solution(int n, String[] words) {
        HashMap<String, Boolean> dict = new HashMap<>();
        int wordIndex = 0;
        int peopleIndex = 0;
        int iter = 1;
        int[] answer = {0, 0};
        String lastWord = "";
        
        while (wordIndex < words.length) {
            String thisWord = words[wordIndex];
            if (dict.get(thisWord) != null) {
                answer[0] = peopleIndex+1;
                answer[1] = iter;
                break;
            }
            if (lastWord.length() > 0) {
                if (lastWord.charAt(lastWord.length()-1) != thisWord.charAt(0)) {
                    answer[0] = peopleIndex+1;
                answer[1] = iter;
                break;
                }
            }
            dict.put(thisWord, true);
            wordIndex++;
            iter = peopleIndex+1>=n ? iter+1:iter;
            peopleIndex = peopleIndex+1>=n ? 0:peopleIndex+1;
            lastWord = thisWord;
        }

        return answer;
    }
}
