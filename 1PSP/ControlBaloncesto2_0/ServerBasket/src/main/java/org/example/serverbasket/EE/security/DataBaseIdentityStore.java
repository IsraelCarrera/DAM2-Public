package org.example.serverbasket.EE.security;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import org.example.common.error.ApiError;
import org.example.common.modelo.Usuario;
import org.example.common.modelo.UsuarioLogging;
import org.example.serverbasket.EE.utils.Constantes;
import org.example.serverbasket.servicios.ServiciosUsuario;

import java.util.Set;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;


@Singleton
public class DataBaseIdentityStore implements IdentityStore {

    private final ServiciosUsuario su;

    @Inject
    public DataBaseIdentityStore(ServiciosUsuario su) {
        this.su = su;
    }

    //Preguntar si hay que dejarlo, o no.
    @Override
    public int priority() {
        return 10;
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        CredentialValidationResult credentialValidationResult = null;
        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential user = (UsernamePasswordCredential) credential;
            //Se consulta el usuario y según sea, 1 o 2, se pone su este.
            Either<ApiError, Usuario> usuario = su.hacerLogging(new UsuarioLogging(user.getCaller(), user.getPasswordAsString()));
            if (usuario.isRight()) {
                switch (usuario.get().getIdTipoUsuario()) {
                    case 1:
                        //Es admin
                        credentialValidationResult = new CredentialValidationResult(usuario.get().getNombreUser(), Set.of(Constantes.ADMIN, Constantes.USER));
                        break;
                    case 2:
                        //Es user
                        credentialValidationResult = new CredentialValidationResult(usuario.get().getNombreUser(), Set.of(Constantes.USER));
                        break;
                    default:
                        credentialValidationResult = INVALID_RESULT;
                        break;
                }
            }
        }
        return credentialValidationResult;
    }

}
