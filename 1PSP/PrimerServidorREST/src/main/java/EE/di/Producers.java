package EE.di;

import jakarta.enterprise.inject.Produces;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

public class Producers {
    @Produces
    public Jsonb producesJsonb()
    {
        return JsonbBuilder.create();
    }
}
