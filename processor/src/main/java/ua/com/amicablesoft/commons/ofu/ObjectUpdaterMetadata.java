package ua.com.amicablesoft.commons.ofu;

import com.google.common.base.Strings;
import com.squareup.javawriter.JavaWriter;

import javax.annotation.processing.Filer;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tymoshenko Bogdan <bogdan.tymoshenko@gmail.com> on 1/21/15.
 */
public class ObjectUpdaterMetadata {
    private final String updaterPackageName;
    private final String updaterClassName;
    private final String targetClassName;
    private final List<Field> targetClassFields = new LinkedList<Field>();


    public ObjectUpdaterMetadata(TypeElement targetTypeElement, Elements elementUtils) {
        PackageElement pkg = elementUtils.getPackageOf(targetTypeElement);
        this.updaterPackageName = packageName(pkg);
        this.targetClassName = targetTypeElement.getSimpleName().toString();
        this.updaterClassName = targetClassName + Constants.UPDATER_CLASS_SUFFIX;
    }

    public void addField(Field field) {
        targetClassFields.add(field);
    }

    public void generateCode(Filer filer) throws IOException {
        JavaFileObject jfo = filer.createSourceFile(updaterClassName);
        Writer writer = jfo.openWriter();
        JavaWriter jw = new JavaWriter(writer);

        // Write package
        jw.emitPackage(updaterPackageName);
        if (!Strings.isNullOrEmpty(updaterPackageName))
            jw.emitEmptyLine();

        // Write imports
        jw.emitImports(ObjectUpdater.class);
        jw.emitImports(Updater.class);
        jw.emitEmptyLine();

        // Write Updater class
        jw.beginType(
                updaterClassName, "class", EnumSet.of(Modifier.PUBLIC), null,
                String.format("%s<%s>", ObjectUpdater.class.getSimpleName(), targetClassName));
        jw.emitEmptyLine();

        // Write Updater update method
        jw.beginMethod("void", "update", EnumSet.of(Modifier.PUBLIC),
                targetClassName, Constants.VARIABLE_NAME__ORIGIN_OBJECT,
                targetClassName, Constants.VARIABLE_NAME__UPDATE_OBJECT);

        for (Field targetClassField : targetClassFields)
            targetClassField.emitUpdateStatement(jw);

        jw.endMethod();

        jw.endType();

        jw.close();
    }

    private String packageName(PackageElement pkg) {
        if (!pkg.isUnnamed())
            return pkg.getQualifiedName().toString();
        else
            return "";
    }
}
