package zh.img.demo;

//import java.awt.image.BufferedImage;  
import java.io.File;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.util.Date;  
  
//import javax.imageio.ImageIO;  
  
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
  
import com.asprise.ocr.Ocr;
//import com.asprise.util.ocr.OCR;

  
public class App {   
    public static String readImg(String url) throws IOException {
    	
    	//String url = "http://business.xiaodingwangplatform.com/localFront/code/getcode.do?t=1490068352540";
        HttpClient httpClient = new HttpClient();  
        //GetMethod getMethod = new GetMethod("http://dz.bjjtgl.gov.cn/service/checkCode.do");  
        GetMethod getMethod = new GetMethod(url);  
        //GetMethod getMethod = new GetMethod("https://dynamic.12306.cn/otsweb/passCodeAction.do?rand=sjrand");  
        int statusCode = httpClient.executeMethod(getMethod);  
        if (statusCode != HttpStatus.SC_OK) {  
            System.err.println("Method failed: " + getMethod.getStatusLine());  
            return "";  
        }  
        String picName = "F:\\img\\";  
        File filepic=new File(picName);  
        if(!filepic.exists())  
            filepic.mkdir();  
        File filepicF=new File(picName+new Date().getTime() + ".jpg");  
        InputStream inputStream = getMethod.getResponseBodyAsStream();  
        OutputStream outStream = new FileOutputStream(filepicF);  
        IOUtils.copy(inputStream, outStream);  
        outStream.close();  
   
        Ocr.setUp(); // one time setup  
        Ocr ocr = new Ocr(); // create a new OCR engine  
        ocr.startEngine("eng", Ocr.SPEED_FASTEST); // English  
        String s = ocr.recognize(new File[] {filepicF},Ocr.RECOGNIZE_TYPE_TEXT, Ocr.OUTPUT_FORMAT_PLAINTEXT);  
        //String str = s.replace(",", "").replace("i", "1").replace(" ", "").replace("'", "").replace("o", "0").replace("O", "0").replace("g", "6").replace("B", "8").replace("s", "5").replace("z", "2");
        String str = s.replace(",", "").replace("i", "1").replace(" ", "").replace("'", "").replace("o", "0").replace("g", "6").replace("s", "5").replace("z", "2");
        System.out.println("Result: " + s);  
        System.out.println("图片文字为:" + str);  
        // ocr more images here ...  
        ocr.stopEngine();  
        return str;
    } 
    
    public static void main(String[] args) throws Exception{
    	String url = "http://business.xiaodingwangplatform.com/localFront/code/getcode.do?t=1490068352540";
    	//String url = "http://mall.dgg.net/passport-l-verifyCode.html?1490161612539";
    	readImg(url);
    }
}  
