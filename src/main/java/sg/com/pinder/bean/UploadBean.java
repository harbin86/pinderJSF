//package sg.com.pinder.bean;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Scanner;
//
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
//import javax.faces.bean.SessionScoped;
//import javax.faces.bean.ViewScoped;
//import javax.servlet.http.Part;
//
//import sg.com.pinder.PinderDriver;
//import sg.com.pinder.pojo.Images;
//import sg.com.pinder.web.WebService;
//
//import org.apache.log4j.Logger;
//
////@ManagedBean
////@SessionScoped
////@ViewScoped
////@RequestScoped
//public class UploadBean {
//
//    private int filesUploaded = 0;
//
//    //javax.servlet.http.Part (Servlet 3.0 API)
//    private Part file;
//    private String fileContent;
//    static Logger logger = Logger.getLogger(UploadBean.class);
//
//    /**
//     * Just prints out file content
//     */
//    public void upload() {
//    		logger.debug("test");
////        try {
////        		Images testImg = new Images();
////            fileContent = new Scanner(file.getInputStream())
////                    .useDelimiter("\\A").next();
////            System.out.println(fileContent + " uploaded");
//            filesUploaded++;
////            Path path = Paths.get(fileContent);
////			byte[] data = Files.readAllBytes(path);
////			testImg.setBytes(data);
////			imageDB.addToDB(testImg);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//    }
//
//    public int getFilesUploaded() {
//        return filesUploaded;
//    }
//
//    public Part getFile() {
//        return file;
//    }
//
//    public void setFile(Part file) {
//        this.file = file;
//    }
//}