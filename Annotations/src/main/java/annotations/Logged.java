package annotations;

import sun.reflect.annotation.AnnotationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(value={ElementType.METHOD, ElementType.TYPE})
public @interface Logged {
}
