// Set 사용법 숙지. HashSet 또한 reference type이기 때문에 다른 Set에 넣을 때 new HashSet<>(set) 등과 같이 새로운 객채로 넣어야 한다.

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Set;
import java.util.HashSet;

class Solution {
    Set<Set<String>> set = new HashSet<>();

    public int solution(String[] user_id, String[] banned_id) {
        check(user_id, banned_id, 0, new boolean[user_id.length], new HashSet<String>());
        return set.size();
    }

    public void check (String[] user, String[] ban, int banIndex, boolean[] c, Set<String> list) {
        if (banIndex == ban.length) {
            set.add(new HashSet<>(list));
            return;
        }

        String regex = ban[banIndex].replace("*", ".");
        Pattern pattern = Pattern.compile(regex);

        for (int i = 0; i < user.length; i++) {
            if (!c[i]) {
                Matcher matcher = pattern.matcher(user[i]);
                if (matcher.matches()) {
                    c[i] = true;
                    list.add(user[i]);
                    check(user, ban, banIndex+1, c, list);
                    list.remove(user[i]);
                    c[i] = false;
                }
            }
        }
    }
}
