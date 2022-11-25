package zw.co.henry.indomidas.apocalypse.service.impl;

import static java.util.Objects.isNull;

import zw.co.henry.indomidas.apocalypse.dto.RobotDTO;
import zw.co.henry.indomidas.apocalypse.repo.RobotRepo;
import zw.co.henry.indomidas.apocalypse.service.RobotService;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zw.co.henry.indomidas.apocalypse.dto.converters.RobotDTOFromRobotConverter;
import zw.co.henry.indomidas.apocalypse.dto.converters.RobotFromRobotDTOConverter;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import zw.co.henry.indomidas.apocalypse.service.RobotSystemAPI;

/**
 * @author henry
 */
@Service
public class RobotServiceImpl implements ApplicationListener<ContextRefreshedEvent>, RobotService
{
   private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RobotServiceImpl.class);

   private final String baseUrl;
   //    private final ContactDTOFromContactResponseConverter contactResponseConverter = new ContactDTOFromContactResponseConverter();
   private Retrofit retrofit = null;

   @Autowired
   private RobotRepo robotRepo;

   public RobotServiceImpl(@Value("${application.restcall.baseUrl:http://localhost:8080}")
   final String baseUrl$) {
      this.baseUrl = baseUrl$;
   }

   @Override
   public List<RobotDTO> connectToRobotCPUSystem() throws IOException
   {
      LOGGER.info("fetchRobotList()");

      RobotSystemAPI service;
      try {
         final Retrofit retrofit$ = getRetrofitClientInstance();
         service = retrofit$.create(RobotSystemAPI.class);
      }
      catch (final Exception exception$) {
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
   public List<RobotDTO> fetchRobotList()
   {
      final RobotDTOFromRobotConverter dtoFromRobotConverter = new RobotDTOFromRobotConverter();
      return robotRepo.findAll().stream().map(dtoFromRobotConverter::convert).filter(Objects::nonNull).collect(Collectors.toList());
   }

   private Retrofit getRetrofitClientInstance() throws IllegalArgumentException
   {
      if (isNull(retrofit)) {
         final ConnectionPool pool = new ConnectionPool();
         //            final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
         //            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

         final OkHttpClient client = new OkHttpClient.Builder()
                                                               //                .addInterceptor(interceptor)
                                                               .connectionPool(pool).build();
         retrofit = new Retrofit.Builder().baseUrl(baseUrl).client(client).addConverterFactory(JacksonConverterFactory.create()).build();
      }
      return retrofit;
   }

//   @Transactional
   @Scheduled(cron = "${service.robots.fetchData.cron.expression}")
   //    @Scheduled(fixedDelay = 600_000)
   public void getData()
   {
      try {
         final RobotFromRobotDTOConverter robotDTOConverter = new RobotFromRobotDTOConverter();
         final List<RobotDTO> robotList = connectToRobotCPUSystem();
         LOGGER.info("{}", robotList);
         robotRepo.deleteAllInBatch();
         robotRepo.saveAll(robotList.stream().map(robotDTOConverter::convert).filter(Objects::nonNull).collect(Collectors.toList()));
      }
      catch (final Exception exception$) {
         LOGGER.error("{}", exception$.getLocalizedMessage());
      }
   }

   @Override
   public void onApplicationEvent(final ContextRefreshedEvent event)
   {
      try {
         //TODO: just ensure robot data loads after start-up regardless of schedule
         this.getData();
      }
      catch (final Exception exception$) {
         LOGGER.error(exception$.getLocalizedMessage());
      }
   }
}
