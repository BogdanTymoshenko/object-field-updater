package ua.com.amicablesoft.commons.ofu.sample;

import ua.com.amicablesoft.commons.ofu.Updatable;

/**
 * Created by Tymoshenko Bogdan <bogdan.tymoshenko@gmail.com> on 1/21/15.
 */
@Updatable
public class Model {
    /*package*/ String stringValue;
    /*package*/ int intValue;

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }
}
