package cn.sp.chapter10.sourceAnnotations;



import jdk.nashorn.internal.objects.annotations.Property;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by 2YSP on 2018/4/29.
 */
@SupportedAnnotationTypes("sourceAnnotations.Property")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class BeanInfoAnnotationProcessor extends AbstractProcessor{


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for(TypeElement t : annotations){
            Map<String,Property> props = new LinkedHashMap<>();
            String beanClassName = null;
            for(Element  e : roundEnv.getElementsAnnotatedWith(t)){
                String mname = e.getSimpleName().toString();
                String[] prefixes = {"get","set","is"};
                boolean found = false;
                for(int i=0;i<prefixes.length && !found;i++){
                    if (mname.startsWith(prefixes[i])){
                        found = true;
                        int start = prefixes[i].length();
                        String name = java.beans.Introspector.decapitalize(mname.substring(start));
                        props.put(name,e.getAnnotation(Property.class));
                    }
                }

                if (!found){
                    processingEnv.getMessager().
                            printMessage(Diagnostic.Kind.ERROR,"@Property must be applied to getXxx,setXxx,or isXxx method",e);
                }else if (beanClassName == null){
                    beanClassName = ((TypeElement)e.getEnclosingElement()).getQualifiedName().toString();
                }
            }

            try {
                if (beanClassName != null){
                    writeBeanInfoFile(beanClassName,props);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * writers the source file for the BeanInfo class
     * @param beanClassName
     * @param props
     */
    private void writeBeanInfoFile(String beanClassName, Map<String, Property> props)throws IOException {
        JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(beanClassName + "BeanInfo");
        PrintWriter out = new PrintWriter(sourceFile.openWriter());
        int i = beanClassName.lastIndexOf('.');
        if (i > 0){
            out.print("package" );
            out.print(beanClassName.substring(0,i));
            out.print(";");
        }

        out.print("public class ");
        out.print(beanClassName.substring(i+1));
        out.println("BeanInfo extends java.beans.SimpleBeanInfo");
        out.println("{");
        out.println(" public java.beans.PropertyDescriptor[] getPropertyDescriptors()");
        out.println(" {");
        out.println("    try");
        out.println("    {");
        //后面打印部分略
        for (Map.Entry<String,Property> e :props.entrySet()){
            out.print("     java.beans.PropertyDescriptor ");
            out.println();
        }
        out.close();
    }
}
