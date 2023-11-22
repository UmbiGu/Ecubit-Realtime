package it.ecubit.xmpp.services.rest;



import it.ecubit.xmpp.services.rest.entity.*;
import it.ecubit.xmpp.services.rest.entity.room.CreateRoom;
import it.ecubit.xmpp.services.rest.entity.room.GetRoomOccupants;
import it.ecubit.xmpp.services.rest.entity.room.RoomOccupants;
import it.ecubit.xmpp.services.rest.wrapperEntity.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import java.util.List;

public interface EjabberdApi {

    @GET("connected_users_info")
    public Call<List<UserInfo>> getUserInfo();

    @Headers({"Accept: application/json"})
    @POST("register")
    public Call<String> registerUser(@Body User user);

    @Headers({"Accept: application/json"})
    @POST("registered_users")
    public Call<List<User>> getUsers(@Body Host host);

    @GET("connected_users_number")
    public Call<NumUserConnected> getConnectedUsersNumber();

    @Headers({"Accept: application/json"})
    @POST("ban_account")
    public Call<String> banUser(@Body BanUser banUser);

    @Headers({"Accept: application/json"})
    @POST("delete_old_users")
    public Call<String> deleteOldUsers(@Body DeleteOldUsers deleteOldUsers);

    @Headers({"Accept: application/json"})
    @POST("get_last")
    public Call<GetLastActivity> getLast(@Body GetLast getLast);

    @POST("get_offline_count")
    public Call<ResponseOfflineCount> getOfflineCount(@Body GetOfflineCount getOfflineCount);

    @Headers({"Accept: application/json"})
    @POST("unregister")
    public Call<String> unregisterUser(@Body Unregister unregister);

    @POST("change_password")
    public Call<String> changePassword(@Body ChangePasswordUser changePasswordUser);

    @POST("check_account")
    public Call<String> accountCheck(@Body UserCheck accountCheck);

    @POST("check_password")
    public Call<String> passwordCheck(@Body User user);

    @Headers({"Accept: application/json"})
    @POST("unban_ip")
    public Call<UnbanWrap> unbanIp(@Body UnbanIp unbanIp);
    
    @Headers({"Accept: application/json"})
    @POST("create_room")
    public Call<String> createRoom(@Body CreateRoom createRoom);

    @Headers({"Accept: application/json"})
    @POST("get_room_occupants")
    public Call<List<RoomOccupants>> getRoomOccupants(@Body GetRoomOccupants getRoomOccupants);
}
