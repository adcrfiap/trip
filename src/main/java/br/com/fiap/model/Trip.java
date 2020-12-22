package br.com.fiap.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;

@DynamoDBTable(tableName = "Trip")
@Data
public class Trip {

    @DynamoDBHashKey(attributeName = "country")
    private String country;

    @DynamoDBIndexRangeKey(attributeName = "city", localSecondaryIndexName = "tagCity")
    private String city;

    @DynamoDBRangeKey(attributeName = "tripDate")
    private String tripDate;

    @DynamoDBAttribute(attributeName = "reason")
    private String reason;

    public Trip(String country, String city, String tripDate, String reason) {
        super();
        this.country = country;
        this.city = city;
        this.tripDate = tripDate;
        this.reason = reason;
    }

    public Trip() {
        super();
    }

}
