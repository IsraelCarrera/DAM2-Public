package org.example.serverserie.ee.security;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import org.example.common.modelo.ApiError;
import org.example.common.modelo.Usuario;
import org.example.serverserie.ee.utils.Constantes;
import org.example.serverserie.model.UsuarioServer;
import org.example.serverserie.servicios.ServiciosUsuarios;

import java.util.Set;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;


@Singleton
public class DataBaseIdentityStore implements IdentityStore {

    private final ServiciosUsuarios su;

    @Inject
    public DataBaseIdentityStore(ServiciosUsuarios su) {
        this.su = su;
    }

    //Preguntar si hay que dejarlo, o no.
    @Override
    public int priority() {
        return 10;
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        CredentialValidationResult credentialValidationResult = INVALID_RESULT;
        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential user = (UsernamePasswordCredential) credential;
            Either<ApiError, UsuarioServer> usuario = su.getUser(user.getCaller(), user.getPasswordAsString());
            if (usuario.isRight()) {
                credentialValidationResult = new CredentialValidationResult(usuario.get().getNombre(), Set.of(Constantes.USER));
            }
        }
        return credentialValidationResult;
    }

}
