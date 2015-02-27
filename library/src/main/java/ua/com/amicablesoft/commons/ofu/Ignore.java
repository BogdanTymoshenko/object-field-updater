package ua.com.amicablesoft.commons.ofu;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by bogdan on 2/26/15.
 */
@Target(ElementType.FIELD) @Retention(RetentionPolicy.CLASS)
public @interface Ignore {
}
