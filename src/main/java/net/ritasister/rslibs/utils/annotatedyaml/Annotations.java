package net.ritasister.rslibs.utils.annotatedyaml;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Annotations {

    /**
     * How field will be named in config.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.TYPE})
    public @interface Key {
        /**
         * The name of this field in the configuration.
         *
         * @return the field's name
         */
        String value();
    }

    /**
     * Creates a comment
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.TYPE})
    public @interface Comment {
        /**
         * The comments to include with this key. Each entry is considered a line.
         *
         * @return the comments
         */
        String[] value();

        /**
         * Enum class with comments constants
         * Value[0] should be an enum constant name
         * Enum should have the getComment() method that returns String[]
         * @return
         */
        Class enumClass() default Object.class;
    }

    /**
     * Indicates that a field cannot be modified
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    public @interface Final {
    }

    /**
     * Indicates that a field should be ignored
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    public @interface Ignore {
    }

}
