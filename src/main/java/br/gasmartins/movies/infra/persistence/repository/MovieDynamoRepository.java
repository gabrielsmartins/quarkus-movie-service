package br.gasmartins.movies.infra.persistence.repository;

import br.gasmartins.movies.infra.persistence.entity.MovieEntity;
import br.gasmartins.movies.infra.persistence.repository.mapper.MovieDynamoRepositoryMapper;
import br.gasmartins.movies.domain.Movie;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class MovieDynamoRepository implements MovieRepository {

    private final DynamoDBMapper dynamoDBMapper;
    private final MovieDynamoRepositoryMapper mapper;


    @Inject
    public MovieDynamoRepository(DynamoDBMapper dynamoDBMapper, MovieDynamoRepositoryMapper mapper) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.mapper = mapper;
    }

    @Override
    public Movie save(Movie movie) {
        var movieEntity = this.mapper.mapToEntity(movie);
        dynamoDBMapper.save(movieEntity);
        movie.setId(movieEntity.getId());
        return mapper.mapToDomain(movieEntity);
    }

    @Override
    public PaginatedScanList<Movie> findAll() {
        var scan = new DynamoDBScanExpression();
        var paginatedScanList = dynamoDBMapper.scan(MovieEntity.class, scan);
        return null;
    }

    @Override
    public PaginatedQueryList<Movie> findByName(String name) {
        var query = new DynamoDBQueryExpression<MovieEntity>();
        var conditions = new HashMap<String, Condition>();
        conditions.put("Name", new Condition().withComparisonOperator(ComparisonOperator.BEGINS_WITH)
                                              .withAttributeValueList(new AttributeValue().withN(name)));
        query.setRangeKeyConditions(conditions);
        var paginatedQueryList = dynamoDBMapper.query(MovieEntity.class, query);
        return null;
    }

    @Override
    public Optional<Movie> findById(UUID id) {
        var movieEntity = this.dynamoDBMapper.load(MovieEntity.class, id);
        return Optional.ofNullable(movieEntity).map(this.mapper::mapToDomain);
    }

    @Override
    public void delete(UUID id) {
        var movieEntity = this.dynamoDBMapper.load(MovieEntity.class, id);
        Optional.ofNullable(movieEntity)
                .ifPresent(this.dynamoDBMapper::delete);
    }
}
