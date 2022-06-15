package me.gabl.library.util.text;

import java.text.MessageFormat;
import java.util.List;

public enum TextFormat {
    RESET(0),
    BOLD(1),
    FAINT(2),
    ITALIC(3),
    UNDERLINE(4),
    BLINK(5),
    RAPID_BLINK(6),
    INVERSE(7),
    CONCEAL(8),
    STRIKETHROUGH(9),
    TEXT_BLACK(30),
    TEXT_RED(31),
    TEXT_GREEN(32),
    TEXT_YELLOW(33),
    TEXT_BLUE(34),
    TEXT_PURPLE(35),
    TEXT_AQUA(36),
    TEXT_GRAY(37),
    TEXT_DEFAULT(39),
    BACKGROUND_BLACK(40),
    BACKGROUND_RED(41),
    BACKGROUND_GREEN(42),
    BACKGROUND_YELLOW(43),
    BACKGROUND_BLUE(44),
    BACKGROUND_PURPLE(45),
    BACKGROUND_AQUA(46),
    BACKGROUND_GRAY(47),
    BACKGROUND_DEFAULT(49),
    TEXT_DARKGRAY(90),
    TEXT_DARKRED(91),
    TEXT_DARKGREEN(92),
    TEXT_GOLD(93),
    TEXT_DARKBLUE(94),
    TEXT_DARKPURPLE(95),
    TEXT_DARKAQUA(96),
    TEXT_WHITE(97),
    BACKGROUND_DARKGRAY(100),
    BACKGROUND_DARKRED(101),
    BACKGROUND_DARKGREEN(102),
    BACKGROUND_GOLD(103),
    BACKGROUND_DARKBLUE(104),
    BACKGROUND_DARKPURPLE(105),
    BACKGROUND_DARKAQUA(106),
    BACKGROUND_WHITE(107);

    private final static char[] indexChars = {(char) 91, (char) 93};
    private final int id;

    TextFormat(int id) {
        this.id = id;
    }

    public static String code(int id) {
        return "" + (char) 27 + (char) 91 + id + (char) 109;
    }

    public static String format(String str) {
        return format(str, true);
    }

    public static String format(String str, boolean useColors, Object... arguments) {
        String value = MessageFormat.format(str, arguments);
        List<Integer> indexes = StringUtils.indexesOf(str, indexChars[0]);
        for(Integer index : indexes) {
            int numbers = 0;
            for(int i = 1; i <= 3; i++) {
                if(StringUtils.stringIsNumber(str.charAt(index + i)))
                    numbers++;
                else
                    break;
            }
            if(str.charAt(index + numbers + 1) == indexChars[1]) {
                int number = Integer.parseInt(str.substring(index + 1, index + numbers + 1));
                if(useColors)
                    value = value.replace("" + indexChars[0] + number + indexChars[1], code(number));
                else
                    value = value.replace("" + indexChars[0] + number + indexChars[1], "");
            }
        }
        return value;
    }

    @Override
    public String toString() {
        return code(this.id);
    }
}