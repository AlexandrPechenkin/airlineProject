package app.util;

import app.exception.ApiValidationException;

public class ApiValidationUtils {

    public static void nonNullRequired(Object object, String message)
            throws ApiValidationException {
        if(object == null) {
            throw new ApiValidationException(message);
        }
    }

    public static void nullRequired(Object object, String message)
            throws ApiValidationException {
        if(object != null) {
            throw new ApiValidationException(message);
        }
    }

    public static void trueRequired(boolean val, String message)
            throws ApiValidationException {
        if(!val) {
            throw new ApiValidationException(message);
        }
    }

    public static void falseRequired(boolean val, String message)
            throws ApiValidationException {
        if(val) {
            throw new ApiValidationException(message);
        }
    }

    public static <T> void equalsRequired(T a, T b, String message)
            throws ApiValidationException {
        if(!a.equals(b)) {
            throw new ApiValidationException(message);
        }
    }
}
