package sg.com.pinder.test;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;
import javax.faces.context.FacesContext;


public class ResourceHandlerWrapperImpl extends
ResourceHandlerWrapper {

private ResourceHandler wrapped=null;

public void ResourceHandlerWrapper(final ResourceHandler wrapped)
{
this.wrapped = wrapped;
}

@Override
public ResourceHandler getWrapped()
{
return wrapped;
}

@Override
public Resource createResource(final String resourceName, final String libraryName)
{
if (AppConstants.RESOURCE_MEDIA_LIB.equals(libraryName))
{
return new MediaResource(resourceName);
}
else
{
return super.createResource(resourceName, libraryName);
}
}

/**
* @see javax.faces.application.ResourceHandlerWrapper#libraryExists(java.lang.String)
*/
@Override
public boolean libraryExists(final String libraryName)
{
if (AppConstants.RESOURCE_MEDIA_LIB.equals(libraryName))
{
return true;
}
else
{
return super.libraryExists(libraryName);
}
}

/**
* @see javax.faces.application.ResourceHandlerWrapper#isResourceRequest(javax.faces.context.FacesContext)
*/
@Override
public boolean isResourceRequest(final FacesContext context)
{
return super.isResourceRequest(context);
}

}

