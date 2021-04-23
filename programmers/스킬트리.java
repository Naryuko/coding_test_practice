class Solution {
    public int solution(String skill, String[] skill_trees) {
        int answer = 0;
        int skillLength = skill.length();
        char[] sk = skill.toCharArray();
        
        firstLoop: for (String tree : skill_trees) {
            boolean[] check = new boolean[skillLength];
            for (int i = 0; i < tree.length(); i++) {
                char treeChar = tree.charAt(i);
                if (ccc (treeChar, sk, check) == 1) {
                    continue firstLoop;
                }
            }
            answer++;
        }
        return answer;
    }
    
    public int ccc (char treeChar, char[] sk, boolean[] check) {
        boolean isValid = true;
        for (int i = 0; i < sk.length; i++) {
            if (isValid && sk[i] == treeChar) {
                check[i] = true;
                return 0;
            } else if (!isValid && sk[i] == treeChar) {
                return 1;
            }
            isValid = check[i];
        }
        return 2;
    }
}
