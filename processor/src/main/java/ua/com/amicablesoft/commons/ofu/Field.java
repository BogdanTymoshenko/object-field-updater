package ua.com.amicablesoft.commons.ofu;

import com.squareup.javawriter.JavaWriter;

import java.io.IOException;

/**
 * Created by Tymoshenko Bogdan <bogdan.tymoshenko@gmail.com> on 1/21/15.
 */
/*package*/ interface Field {
    void emitUpdateStatement(JavaWriter writer) throws IOException;
}
