package ir.ma.snippets.annotation.api;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.tools.Diagnostic;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SupportedAnnotationTypes(
        "ir.ma.snippets.annotation.api.Check")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CheckProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements
                    = roundEnv.getElementsAnnotatedWith(annotation);
            annotatedElements.stream()
                    .filter(o -> o instanceof ExecutableElement)
                    .map(o -> (ExecutableElement)o)
                    .filter(o -> !o.getReturnType().toString().startsWith("java.util.concurrent.CompletableFuture<"))
                    .map(o -> o.getReturnType().toString())
                    .forEach(o -> processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Method does not return async result:" + o));
//            Map<Boolean, List<Element>> annotatedMethods = annotatedElements.stream().collect(
//                    Collectors.partitioningBy(element ->
//                            ((ExecutableType) element.asType()).getParameterTypes().size() == 1
//                                    && element.getSimpleName().toString().startsWith("set")));
//
//            List<Element> setters = annotatedMethods.get(true);
//            List<Element> otherMethods = annotatedMethods.get(false);
        }
        return false;
    }
}
