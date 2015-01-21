package ua.com.amicablesoft.commons.ofu;

import com.squareup.javawriter.JavaWriter;

import java.io.IOException;

/**
 * Created by Tymoshenko Bogdan <bogdan.tymoshenko@gmail.com> on 1/21/15.
 */
public class ImmutableField implements Field {

    private final String name;

    public ImmutableField(String name) {
        this.name = name;
    }

    @Override
    public void emitUpdateStatement(JavaWriter writer) throws IOException {
        writer.emitStatement("%s.%s = %s.%s",
                Constants.VARIABLE_NAME__ORIGIN_OBJECT, name,
                Constants.VARIABLE_NAME__UPDATE_OBJECT, name);
    }
}
