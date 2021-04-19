import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.PriorityQueue;
import java.util.Comparator;
class Solution {
    boolean[] check = new boolean[100001];

    public int[] solution(String s) {
        int count = 0;
        PriorityQueue<String[]> pq = new PriorityQueue<> (new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                return o1.length - o2.length;
            }
        });

        Pattern pattern = Pattern.compile("([0-9]+[,])*[0-9]+");
        Matcher matcher = pattern.matcher(s);

        while (matcher.find()) {
            String temp = matcher.group();
            String[] tt = temp.split(",");
            pq.add(tt);
            count++;
        }

        int[] answer = new int[count];
        int j = 0;

        for (int i = 0; i < count; i++) {
            String[] temp = pq.poll();
            for (String ss : temp) {
                int sss = Integer.parseInt(ss);
                if (!check[sss]) {
                    check[sss] = true;
                    answer[j] = sss;
                    j++;
                }
            }
        }

        return answer;
    }

//    public static void main (String[] arg) {
//        String s = "{{20,111},{111}}";
//        Solution ss = new Solution();
//        int[] temp = ss.solution(s);
//        for (int i : temp) {
//            System.out.printf("%d ",i);
//        }
//        System.out.println();
//    }
}
