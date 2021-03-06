package sg.com.pinder.shiro.authc;

import java.security.cert.X509Certificate;
/**
 * Token for certificate based authentication. The certificate is used as both principal and credentials. 
 *
 */
@SuppressWarnings("serial")
public class X509CertificateOnlyAuthenticationToken implements X509CertificateAuthenticationToken {

	//certificate is used as both credentials and principal
	private X509Certificate certificate;
	
	public X509CertificateOnlyAuthenticationToken() {
	  super();
	}

	public X509CertificateOnlyAuthenticationToken(X509Certificate certificate) {
	  super();
	  this.certificate = certificate;
  }

  public Object getPrincipal() {
	  return getCertificate();
  }

  public Object getCredentials() {
	  return getCertificate();
  }

	/* (non-Javadoc)
   * @see org.meri.simpleshirosecuredapplication.authc.X509CertificateAuthenticationToken#getCertificate()
   */
  public X509Certificate getCertificate() {
  	return certificate;
  }

	public void setCertificate(X509Certificate certificate) {
  	this.certificate = certificate;
  }

}
