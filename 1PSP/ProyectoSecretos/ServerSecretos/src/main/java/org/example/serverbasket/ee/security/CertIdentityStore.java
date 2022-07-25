package org.example.serverbasket.ee.security;

import com.nimbusds.jose.util.X509CertUtils;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import org.example.serverbasket.ee.utils.Constantes;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Set;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

@Singleton
public class CertIdentityStore implements IdentityStore {
    private final GetClavesDoCertf getClavesDoCertf;

    @Inject
    public CertIdentityStore(GetClavesDoCertf getClavesDoCertf) {
        this.getClavesDoCertf = getClavesDoCertf;
    }


    //Preguntar si hay que dejarlo, o no.
    @Override
    public int priority() {
        return 10;
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        CredentialValidationResult credentialValidationResult = null;
        try {
            if (credential instanceof UsernamePasswordCredential) {
                UsernamePasswordCredential user = (UsernamePasswordCredential) credential;
                X509Certificate cert = X509CertUtils.parse(Base64.getUrlDecoder().decode(user.getPasswordAsString()));
                Either<String, PublicKey> eitherPublicKey = getClavesDoCertf.getPublicKey();
                if (eitherPublicKey.isRight()) {
                    //Siendo correcto, verificamos el certificado firmado por el servidor.
                    cert.verify(eitherPublicKey.get());
                    //Si no lanza excepción, es que es correcta.
                    credentialValidationResult = new CredentialValidationResult(user.getCaller(), Set.of(Constantes.USER));
                } else {
                    credentialValidationResult = INVALID_RESULT;
                }
            }
        } catch (Exception e) {
            credentialValidationResult = INVALID_RESULT;
        }
        return credentialValidationResult;
    }
}
