package android.text;

/**
 * Created by lijinkui on 2017/5/7.
 */

public class TextUtils {

    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.equals("")) {
            return true;
        }
        return false;
    }
}
