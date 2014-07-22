package com.github.dylon.liblevenshtein.annotation.processor;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import com.github.dylon.liblevenshtein.annotation.Experimental;

@SupportedAnnotationTypes(
	"com.github.dylon.liblevenshtein.annotation.Experimental"
)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ExperimentalProcessor extends AbstractProcessor {

	@Override
	public boolean process(
			final Set<? extends TypeElement> annotations,
			final RoundEnvironment roundEnv) {

		if (!roundEnv.processingOver()) {
			final Set<? extends Element> elements =
				roundEnv.getElementsAnnotatedWith(Experimental.class);

			final Iterator<? extends Element> iterator = elements.iterator();
			while (iterator.hasNext()) {
				final Element element = iterator.next();
				final Experimental annotation = element.getAnnotation(Experimental.class);
				final String warning = element + ": " + annotation.value();
				processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, warning);
			}
		}

		return true;
	}
}
