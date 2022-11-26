package zw.co.henry.indomidas.apocalypse.service.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import zw.co.henry.indomidas.apocalypse.dto.RobotDTO;
import zw.co.henry.indomidas.apocalypse.dto.converters.RobotDTOFromRobotConverter;
import zw.co.henry.indomidas.apocalypse.dto.converters.RobotFromRobotDTOConverter;
import zw.co.henry.indomidas.apocalypse.model.data.SingleSeries;
import zw.co.henry.indomidas.apocalypse.model.response.SingleDataSeriesResponse;
import zw.co.henry.indomidas.apocalypse.model.robot.QRobot;
import zw.co.henry.indomidas.apocalypse.model.robot.Robot;
import zw.co.henry.indomidas.apocalypse.model.robot.RobotResponse;
import zw.co.henry.indomidas.apocalypse.repo.RobotRepo;
import zw.co.henry.indomidas.apocalypse.service.RobotService;
import zw.co.henry.indomidas.apocalypse.service.RobotSystemAPI;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static zw.co.henry.indomidas.apocalypse.model.response.OperationResponse.ResponseStatusEnum.SUCCESS;
import static java.util.Objects.isNull;

/**
 * @author henry
 */
@Service
@Slf4j
public class RobotServiceImpl implements ApplicationListener<ContextRefreshedEvent>, RobotService {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RobotServiceImpl.class);

    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    private Retrofit retrofit = null;

    private final String baseUrl;

    private final RobotDTOFromRobotConverter dtoFromRobotConverter = new RobotDTOFromRobotConverter();
    private final RobotFromRobotDTOConverter dtoToRobotConverter = new RobotFromRobotDTOConverter();
    private final QRobot qRobot = QRobot.robot;

    @Autowired
    private RobotRepo robotRepo;

    public RobotServiceImpl(@Value("${application.restcall.baseUrl:http://localhost:8080}") final String baseUrl$) {
        this.baseUrl = baseUrl$;
    }

    @Override
    public List<RobotDTO> connectToRobotCPUSystem() throws IOException {
        LOGGER.debug("connectToRobotCPUSystem()");

        RobotSystemAPI service;
        try {
            final Retrofit retrofit$ = getRetrofitClientInstance();
            service = retrofit$.create(RobotSystemAPI.class);
        } catch (final Exception exception$) {
            LOGGER.error("{}", exception$.getLocalizedMessage());
            throw new RuntimeException("FATAL ERROR GENERATING REST CLIENT: " + exception$.getLocalizedMessage());
        }

        final Response<List<RobotDTO>> response = service.fetchRobotList().execute();
        if (!response.isSuccessful()) {
            throw new IOException(Objects.requireNonNull(response.errorBody()).string());
        }

        return response.body();
    }

    @Override
    public List<RobotDTO> fetchRobotList() {
        log.debug("fetchRobotList()");
        return robotRepo.findAll().stream().map(dtoFromRobotConverter::convert).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public SingleDataSeriesResponse getRobotStatsForType(final String type$) {
        log.debug("getRobotStatsForType({})", type$);

        NumberExpression<Integer> value;
        if ("status".equalsIgnoreCase(type$) || "order_status".equalsIgnoreCase(type$)) {
            value = Wildcard.countAsInt.as("value");
        } else {
            value = Wildcard.countAsInt.as("value");
        }

        final JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);
        final List<Tuple> result = jpaQueryFactory.select(value
                        , qRobot.category.as("name"))
                .from(qRobot)
                .groupBy(qRobot.category).fetch();

        final List<SingleSeries> dataItemList = result.stream().
                map(o -> {
                            final Optional<Integer> optionalValue = Optional.ofNullable(o.get(0, Integer.class));
                            if (optionalValue.isPresent()) {
                                final Integer value0 = optionalValue.get();
                                return new SingleSeries(o.get(1, String.class), new BigDecimal(value0));
                            } else {
                                return null;
                            }
                        }
                )
                .collect(Collectors.toList());

        SingleDataSeriesResponse response = new SingleDataSeriesResponse();
        response.setItems(dataItemList);
        response.setOperationStatus(SUCCESS);
        response.setOperationMessage("Robots by " + type$);
        return response;
    }

    @Override
    public RobotResponse getListOfRobotsByPage(final String category$, final Pageable pageable$) {
        log.debug("getListOfRobotsByPage({})", category$);
        final Robot qry = new Robot();
        if (category$ != null) {
            qry.setCategory(category$);
        }

        final Page<Robot> page = robotRepo.findAll(Example.of(qry), pageable$);
        final RobotResponse response = new RobotResponse();
        response.setPageStats(page, true);
        response.setItems(page.getContent().stream().map(dtoFromRobotConverter::convert).collect(Collectors.toList()));
        response.setOperationMessage(MessageFormat.format("Robots page {0} of {1} // {2} robots found", pageable$.getPageNumber(), page.getTotalPages(), page.getTotalElements()));
        return response;
    }

    private Retrofit getRetrofitClientInstance() throws IllegalArgumentException {
        if (isNull(retrofit)) {
            final ConnectionPool pool = new ConnectionPool();
            final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

            final OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectionPool(pool).build();
            retrofit = new Retrofit.Builder().baseUrl(baseUrl).client(client).addConverterFactory(JacksonConverterFactory.create()).build();
        }
        return retrofit;
    }

    //   @Transactional
    @Scheduled(cron = "${service.robots.fetchData.cron.expression}")
    //    @Scheduled(fixedDelay = 600_000)
    public void getData() {
        try {
            final List<RobotDTO> robotList = connectToRobotCPUSystem();
            LOGGER.trace("{}", robotList);
            robotRepo.deleteAllInBatch();
            robotRepo.saveAll(robotList.stream().map(dtoToRobotConverter::convert).filter(Objects::nonNull).collect(Collectors.toList()));
        } catch (final Exception exception$) {
            LOGGER.error("{}", exception$.getLocalizedMessage());
        }
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        try {
            //TODO: just ensure robot data loads after start-up regardless of schedule
            this.getData();
        } catch (final Exception exception$) {
            LOGGER.error(exception$.getLocalizedMessage());
        }
    }
}
