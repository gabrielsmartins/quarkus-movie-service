package br.gasmartins.movies.infra.persistence.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static br.gasmartins.movies.domain.support.MovieSupport.defaultMovie;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class MovieDynamoRepositoryTest {

    @Inject
    private MovieDynamoRepository repository;

    @Inject
    private DynamoDBMapper dynamoDBMapper;

    @Inject
    private AmazonDynamoDB amazonDynamoDB;
    private static DynamoDBProxyServer server;

    @BeforeAll
    public static void setupAll() throws Exception {
        System.setProperty("sqlite4java.library.path", "native-libs");
        String port = "8000";
        server = ServerRunner.createServerFromCommandLineArgs(new String[]{"-inMemory", "-port", port});
        server.start();
    }

    @AfterAll
    public static void tearDownAll() throws Exception {
        server.stop();
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
        var paginatedScanList = this.repository.findAll();
        assertThat(paginatedScanList).isNotEmpty();
    }

    @Test
    @DisplayName("Given Movie Name When Exists Then Return Movie List")
    public void givenMovieNameWhenExistsThenReturnMovieList(){
        var movie = defaultMovie().withId(null)
                                         .build();
        this.repository.save(movie);
        var paginatedQueryList = this.repository.findByName(movie.getName());
        assertThat(paginatedQueryList).isNotEmpty();
    }
}
