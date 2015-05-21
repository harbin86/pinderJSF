package sg.com.pinder.mediahandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;

import com.sun.faces.util.Util;

public class MediaResource extends Resource {

    private final String mediaId;

    public MediaResource(final String mediaId) {
        setLibraryName("yo");//AppConstants.RESOURCE_MEDIA_LIB
        setResourceName(mediaId);
        setContentType("image/png");
        this.mediaId = mediaId;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (mediaId != null) {
            System.out.println("Yeahhh!!!");
        }

        return null;
    }

    @Override
    public Map<String, String> getResponseHeaders() {
        return new HashMap<String, String>();
    }

    @Override
    public String getRequestPath() {
        final FacesContext context = FacesContext.getCurrentInstance();
        return context
                .getApplication()
                .getViewHandler()
                .getResourceURL(
                        context,
                        ResourceHandler.RESOURCE_IDENTIFIER + "/" + mediaId + Util.getFacesMapping(context)
                                + "?ln=yo");//AppConstants.RESOURCE_MEDIA_LIB
    }

    @Override
    public URL getURL() {
        return null;
    }

    @Override
    public boolean userAgentNeedsUpdate(final FacesContext context) {
        return true;
    }

}