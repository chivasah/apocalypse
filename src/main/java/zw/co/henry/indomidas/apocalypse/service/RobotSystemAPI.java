package zw.co.henry.indomidas.apocalypse.service;

import java.util.List;


import retrofit2.Call;
import retrofit2.http.GET;
import zw.co.henry.indomidas.apocalypse.dto.RobotDTO;

/**
 * @author henry
 */
public interface RobotSystemAPI
{
   @GET("/robotcpu")
   Call<List<RobotDTO>> fetchRobotList();
}
