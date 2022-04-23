package br.gasmartins.movies.infra.persistence.repository;

import br.gasmartins.movies.domain.PageRequest;
import br.gasmartins.movies.infra.persistence.entity.MovieEntity;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.model.BillingMode;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;

import static br.gasmartins.movies.domain.support.MovieSupport.defaultMovie;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@ExtendWith(DynamoDBExtension.class)
public class MovieDynamoRepositoryTest {

    @Inject
    private MovieDynamoRepository repository;

    @Inject
    private DynamoDBMapper dynamoDBMapper;

    @Inject
    private AmazonDynamoDB amazonDynamoDB;

    private static final String TABLE_NAME = "Movies";

    @BeforeEach
    public void setup() {
        try {
        var config = new DynamoDBMapperConfig.TableNameOverride(TABLE_NAME).config();
        var tableRequest = dynamoDBMapper.generateCreateTableRequest(MovieEntity.class, config);
        tableRequest.setBillingMode(BillingMode.PAY_PER_REQUEST.toString());
        //tableRequest.setProvisionedThroughput(new ProvisionedThroughput(5L, 5L));
        amazonDynamoDB.createTable(tableRequest);
        } catch (ResourceInUseException e) {
            // Do nothing, table already created
        }
    }

    @Test
    @DisplayName("Given Movie When Save Then Return Saved Movie")
    public void givenMovieWhenSaveThenReturnSavedMovie(){
        var movie = defaultMovie().withId(null)
                                         .build();
        var savedMovie = this.repository.save(movie);
        assertThat(savedMovie).isNotNull();
    }

    @Test
    @DisplayName("Given Movie Id When Exists Then Return Movie")
    public void givenMovieIdWhenExistsThenReturnMovie(){
        var movie = defaultMovie().withId(null)
                                         .build();
        this.repository.save(movie);
        var optionalMovie = this.repository.findById(movie.getId());
        assertThat(optionalMovie).isPresent();
    }


    @Test
    @DisplayName("Given Movie Id When Exists Then Delete Movie")
    public void givenMovieIdWhenExistsThenDeleteMovie(){
        var movie = defaultMovie().withId(null)
                                         .build();
        this.repository.save(movie);
        this.repository.delete(movie.getId());
    }

    @Test
    @DisplayName("Given Movie List When Exists Then Return Movie List")
    public void givenMovieListWhenExistsThenReturnMovieList(){
        var movie = defaultMovie().withId(null)
                                         .build();
        this.repository.save(movie);
        var page = this.repository.findAll(PageRequest.of(0, 30));
        assertThat(page.getContent()).isNotEmpty();
    }

    @Test
    @DisplayName("Given Movie Name When Exists Then Return Movie List")
    public void givenMovieNameWhenExistsThenReturnMovieList(){
        var movie = defaultMovie().withId(null)
                                         .build();
        this.repository.save(movie);
        var page = this.repository.findByName(movie.getName(), PageRequest.of(0, 30));
        assertThat(page.getContent()).isNotEmpty();
    }
}
