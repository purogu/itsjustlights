package purogu.itsjustlights;

public class Utils {
    public static String toTitleCase(String s) {
        String[] split = s.split(" ");
        String[] titleCased = new String[split.length];
        for(int i = 0; i < split.length; i++) {
            titleCased[i] = Character.toUpperCase(split[i].charAt(0))
                    + split[i].substring(1).toLowerCase();
        }

        return String.join(" ", titleCased);
    }

}
