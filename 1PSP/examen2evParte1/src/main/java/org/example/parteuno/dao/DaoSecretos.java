package org.example.parteuno.dao;

import io.vavr.control.Either;
import org.example.parteuno.modelo.Secreto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DaoSecretos {
    private static final List<Secreto> secretos = new ArrayList<>();


    public DaoSecretos() {
    }

    //Add secreto
    public Either<String, Boolean> addSecreto(Secreto secreto){
        Either<String,Boolean> resultado;
        if(secretos.add(secreto)){
            resultado = Either.right(true);
        }else{
            resultado = Either.left("No se ha podido añadir dicho secreto");
        }
        return resultado;
    }

    //Consultar secreto
    public Either<String, List<Secreto>> consultarSecreto(String nombre){
        Either<String,List<Secreto>> resultado;
        List<Secreto> s = secretos.stream()
                .filter(secreto -> secreto.getNombre().equals(nombre))
                .collect(Collectors.toList());
        if(!s.isEmpty()){
            resultado = Either.right(s);
        }else{
         resultado = Either.left("No hay secretos con dicho nombre ");
        }
        return resultado;
    }
}
