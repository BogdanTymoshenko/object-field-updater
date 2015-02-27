package ua.com.amicablesoft.commons.ofu;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Created by Tymoshenko Bogdan <bogdan.tymoshenko@gmail.com> on 1/21/15.
 */
@AutoService(Processor.class)
public class ObjectFieldUpdaterProcessor extends AbstractProcessor {

    private static final String SUFFIX = "Updater";

    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Updatable.class)) {
            // Check if a class has been annotated with @Updatable
            if (annotatedElement.getKind() != ElementKind.CLASS) {
                error(annotatedElement, "Only classes can be annotated with @%s", Updatable.class.getSimpleName());
                return true;
            }

            // We can cast it, because we know that it of ElementKind.CLASS
            TypeElement typeElement = (TypeElement) annotatedElement;
            if (!isValidClass(typeElement))
                return true; // Error message printed, exit processing

            ObjectUpdaterMetadata updaterMetadata = new ObjectUpdaterMetadata(typeElement, elementUtils);

            for (Element element : typeElement.getEnclosedElements()) {
                if (element.getKind() == ElementKind.FIELD) {
                    VariableElement field = (VariableElement) element;
                    if (field.getModifiers().contains(Modifier.FINAL)){
                        // skip final field
                        continue;
                    }

                    if (field.getAnnotation(Ignore.class) instanceof Ignore) {
                        // skip ignored fields
                        continue;
                    }
                    if (field.asType().getKind() == TypeKind.DECLARED) {
                        if (isFieldUpdatableClass(field.asType())) {
                            // this is updatable field
                            updaterMetadata.addField(new UpdatableField(field.getSimpleName().toString()));
                            continue;
                        }
                    }

                    // this is another field type - handle as immutable
                    updaterMetadata.addField(new ImmutableField(field.getSimpleName().toString()));
                }
            }

            try {
                updaterMetadata.generateCode(filer);
            }
            catch (IOException e) {
                error(null, e.getMessage());
            }
        }

        return true;
    }

    private boolean isFieldUpdatableListClass(VariableElement field) {
        Element fieldElement = typeUtils.asElement(field.asType());
        if (fieldElement.getKind() == ElementKind.INTERFACE) {
            TypeElement typeElement = (TypeElement) fieldElement;
            if (typeElement.getQualifiedName().contentEquals(List.class.getName())) {
                if (field.asType() instanceof DeclaredType) {
                    DeclaredType declaredType = (DeclaredType) field.asType();
                    for (TypeMirror typeMirror : declaredType.getTypeArguments())
                        if (isFieldUpdatableClass(typeMirror))
                            return true;
                }
            }
        }

        return false;
    }

    private boolean isFieldUpdatableClass(TypeMirror fieldTypeMirror) {
        return typeUtils.asElement(fieldTypeMirror).getAnnotation(Updatable.class) instanceof Updatable;
    }

    private boolean isFieldMarkedIgnored(TypeMirror fieldTypeMirror) {
        return typeUtils.asElement(fieldTypeMirror).getAnnotation(Ignore.class) instanceof Ignore;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<String>();
        annotations.add(Updatable.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private boolean isValidClass(TypeElement classElement) {
        if (!classElement.getModifiers().contains(Modifier.PUBLIC)) {
            error(classElement, "The class %s is not public.", classElement.getQualifiedName().toString());
            return false;
        }

        // Check if it's an abstract class
        if (classElement.getModifiers().contains(Modifier.ABSTRACT)) {
            error(classElement, "The class %s is abstract. You can't annotate abstract classes with @%",
                    classElement.getQualifiedName().toString(), Updatable.class.getSimpleName());
            return false;
        }

        return true;
    }

    private void error(Element e, String msg, Object... args) {
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), e);
    }
}
