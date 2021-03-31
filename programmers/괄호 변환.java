
public class Solution {
    public String solution(String p) {
        return get(p);
    }

    public static String get (String s) {
        if (s.length() == 0) {
            return "";
        }

        String ret = "";
        String[] temp = divide(s);
        if (correctString(temp[0])) {
            ret = ret + temp[0] + get(temp[1]);
        } else {
            ret = ret + "(" + get(temp[1]) + ")" + reverse(temp[0].substring(1,temp[0].length()-1));
        }

        return ret;
    }

    public static String reverse (String s) {
        String ret = "";
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '(':
                    ret += ")";
                    break;
                case ')':
                    ret += "(";
                    break;
            }
        }

        return ret;
    }

    public static String[] divide (String s) {
        int count = 0;
        int index = 0;
        String u = "";
        String v = "";

        while (index < s.length()) {
            switch (s.charAt(index)) {
                case '(':
                    count++;
                    break;
                case ')':
                    count--;
                    break;
            }

            if (count == 0) {
                break;
            }
            index++;
        }

        u = s.substring(0,index+1);
        v = s.substring(index+1);

        return new String[] {u, v};
    }

    public static boolean correctString (String u) {
        int count = 0;
        int index = 0;
        boolean ret = false;

        while (index < u.length()) {
            switch (u.charAt(index)) {
                case '(':
                    count++;
                    break;
                case ')':
                    count--;
                    break;
            }
            if (count < 0) {
                break;
            } else if (count == 0) {
                ret = true;
                break;
            }
            index++;
        }

        return ret;
    }

}
