//import java.io.IOException;
//
//import javax.faces.application.FacesMessage;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
//import javax.faces.context.FacesContext;
//
//import org.apache.commons.io.FilenameUtils;
//import org.apache.myfaces.custom.fileupload.UploadedFile;
//
//@ManagedBean
//@RequestScoped
//public class UploadFile {
//
//    private UploadedFile uploadedFile;
//
//    public void submit() throws IOException {
//        String fileName = FilenameUtils.getName(uploadedFile.getName());
//        String contentType = uploadedFile.getContentType();
//        byte[] bytes = uploadedFile.getBytes();
//
//        // Now you can save bytes in DB (and also content type?)
//
//        FacesContext.getCurrentInstance().addMessage(null, 
//            new FacesMessage(String.format("File '%s' of type '%s' successfully uploaded!", fileName, contentType)));
//    }
//
//    public UploadedFile getUploadedFile() {
//        return uploadedFile;
//    }
//
//    public void setUploadedFile(UploadedFile uploadedFile) {
//        this.uploadedFile = uploadedFile;
//    }
//
//}