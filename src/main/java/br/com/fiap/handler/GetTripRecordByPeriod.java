package br.com.fiap.handler;

import br.com.fiap.dao.TripRepository;
import br.com.fiap.model.HandlerRequest;
import br.com.fiap.model.HandlerResponse;
import br.com.fiap.model.Trip;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.List;

public class GetTripRecordByPeriod implements RequestHandler<HandlerRequest, HandlerResponse> {

    private  final TripRepository repository = new TripRepository();

    @Override
    public HandlerResponse handleRequest(HandlerRequest request, Context context) {
        try {
            final String country = request.getPathParameters().get("country");
            final String starts = request.getQueryStringParameters().get("starts");
            final String ends = request.getQueryStringParameters().get("ends");

            context.getLogger().log("Searching records between " + starts + " and " + ends);
            final List<Trip> trips = this.repository.findByPeriod(country,starts, ends);

            if(trips == null || trips.isEmpty()) {
                return HandlerResponse.builder().setStatusCode(404).build();
            }

            return HandlerResponse.builder().setStatusCode(200).setObjectBody(trips).build();
        }catch (Exception e){
            return HandlerResponse.builder().setStatusCode(500).setObjectBody(e.getMessage()).build();
        }

    }
}
