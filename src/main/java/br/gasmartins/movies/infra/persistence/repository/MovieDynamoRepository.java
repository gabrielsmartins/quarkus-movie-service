package br.gasmartins.movies.infra.persistence.repository;

import br.gasmartins.movies.domain.*;
import br.gasmartins.movies.infra.persistence.entity.MovieEntity;
import br.gasmartins.movies.infra.persistence.repository.mapper.MovieDynamoRepositoryMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public Page<Movie> findAll(Pageable pageable) {
        var scan = new DynamoDBScanExpression();
        var paginatedScanList = dynamoDBMapper.scan(MovieEntity.class, scan);
        var movies = paginatedScanList.stream().map(this.mapper::mapToDomain).collect(Collectors.toList());
        return new PageImpl<>(movies, pageable, paginatedScanList.size());
    }

    @Override
    public Page<Movie> findByName(String name, Pageable pageable) {
        var conditions = new HashMap<String, Condition>();
        conditions.put("Name", new Condition().withComparisonOperator(ComparisonOperator.BEGINS_WITH)
                .withAttributeValueList(new AttributeValue().withS(name)));
        var scan = new DynamoDBScanExpression()
                                             .withScanFilter(conditions);
        var paginatedQueryList = dynamoDBMapper.scan(MovieEntity.class, scan);
        var movies = paginatedQueryList.stream().map(this.mapper::mapToDomain).collect(Collectors.toList());
        return new PageImpl<>(movies, pageable, paginatedQueryList.size());
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
