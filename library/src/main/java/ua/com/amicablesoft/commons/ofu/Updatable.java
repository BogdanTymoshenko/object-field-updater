package ua.com.amicablesoft.commons.ofu;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Tymoshenko Bogdan <bogdan.tymoshenko@gmail.com> on 1/21/15.
 */
@Target(ElementType.TYPE) @Retention(RetentionPolicy.CLASS)
public @interface Updatable {
}
