package br.com.fiap.dao;

import br.com.fiap.model.Trip;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TripRepository {

    private static final DynamoDBMapper mapper = DynamoDBManager.mapper();

    public Trip save(final Trip tirp) {
        mapper.save(tirp);
        return tirp;
    }

    public List<Trip> findByPeriod(final String country, final String starts, final String ends) {

        final Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(starts));
        eav.put(":val2", new AttributeValue().withS(ends));
        eav.put(":val3", new AttributeValue().withS(country));

        final DynamoDBQueryExpression<Trip> queryExpression = new DynamoDBQueryExpression<Trip>()
                .withKeyConditionExpression("country= :val3 and tripDate between :val1 and :val2")
                .withExpressionAttributeValues(eav);

        final List<Trip> trips = mapper.query(Trip.class, queryExpression);

        return trips;
    }

    public List<Trip> findByCountry(final String country) {

        final Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(country));

        final DynamoDBQueryExpression<Trip> queryExpression = new DynamoDBQueryExpression<Trip>()
                .withKeyConditionExpression("country= :val1")
                .withExpressionAttributeValues(eav);

        final List<Trip> trips = mapper.query(Trip.class, queryExpression);

        return trips;
    }

    public List<Trip> findByCity(final String country, final String city) {

        final Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(country));
        eav.put(":val2", new AttributeValue().withS(city));

        final DynamoDBQueryExpression<Trip> queryExpression = new DynamoDBQueryExpression<Trip>()
                .withIndexName("cityIndex").withConsistentRead(false)
                .withKeyConditionExpression("country = :val1 and city=:val2").withExpressionAttributeValues(eav);

        final List<Trip> trips = mapper.query(Trip.class, queryExpression);

        return trips;
    }

}
