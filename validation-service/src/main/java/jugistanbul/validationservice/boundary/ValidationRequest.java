package jugistanbul.validationservice.boundary;

import org.slf4j.Logger;

import javax.inject.Inject;
import javax.json.Json;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author hakdogan (hakdogan@kodcu.com)
 * Created on 17.08.2020
 **/
@Path("validation")
public class ValidationRequest
{
    @Inject
    private Logger logger;

    @GET
    @Path("{cardNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response validate(@PathParam("cardNumber") final String cardNumber){
        final boolean isValid = validateCardNumber(cardNumber);

        if(!isValid){
            logger.info("Invalid Credit Card Number {}", cardNumber);
            return Response.status(200)
                    .entity(Json.createObjectBuilder()
                            .add("message", "Invalid Credit Card Number")
                            .add("approval", false)
                            .build())
                    .build();
        }

        return Response
                .status(200)
                .entity(Json.createObjectBuilder()
                        .add("message", "Valid Credit Card Number")
                        .add("approval", true)
                        .build())
                .build();
    }

    private boolean validateCardNumber(final String cardNumber){
        try {
            Long.parseLong(cardNumber);
            return true;
        } catch (NumberFormatException ex){ }
        return false;
    }
}
