package it.ecubit.xmpp.services.rest;

import com.google.gson.Gson;
import it.ecubit.xmpp.services.exception.ExceptionGeneric;
import it.ecubit.xmpp.services.rest.entity.*;
import it.ecubit.xmpp.services.rest.entity.room.CreateRoom;
import it.ecubit.xmpp.services.rest.entity.room.GetRoomOccupants;
import it.ecubit.xmpp.services.rest.entity.room.RoomOccupants;
import it.ecubit.xmpp.services.rest.wrapperEntity.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.jws.soap.SOAPBinding;
import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.util.List;

public class EjabberdClient {

	Retrofit retrofit;
	EjabberdApi ejabberdApi;
	static EjabberdClient client;

	public EjabberdClient(String host) {
		retrofit = new Retrofit.Builder().baseUrl(host)
				.addConverterFactory(GsonConverterFactory.create(new Gson()))
				.build();
		ejabberdApi = retrofit.create(EjabberdApi.class);
	}

	public static synchronized EjabberdClient getInstance(String host){
		if (client == null)
			client = new EjabberdClient(host);
		return client;
	}

	public List<UserInfo> connectedUsers() throws IOException {
		Call<List<UserInfo>> userInfoList = ejabberdApi.getUserInfo();
		Response<List<UserInfo>> response = userInfoList.execute();
		List<UserInfo> userInfos = response.body();
		return userInfos;
	}

	public void registerUser(User user) throws ExceptionGeneric, IOException {
		Call<String> register = ejabberdApi.registerUser(user);
		register.enqueue(new Callback<String>() {
			@Override
			public void onResponse(Call<String> call, Response<String> response) {
				if (response.isSuccessful()){
					String responseString = response.body();
				}
			}
			@Override
			public void onFailure(Call<String> call, Throwable t) {
				t.addSuppressed(new ExceptionGeneric("Errore Generico"));
				t.addSuppressed(new IOException("Errore boh"));
			}
		});
	}

	public List<User> getRegisterUsers() throws IOException {
		Host h = new Host("localhost");
		Call<List> register = ejabberdApi.getUsers(h);
		Response<List> registerResponse = register.execute();
		return registerResponse.body();
	}

	public NumUserConnected getConnectedUsersNumber() throws IOException {
		Call<NumUserConnected> connectedUsersNumber = ejabberdApi.getConnectedUsersNumber();
		Response<NumUserConnected> connectedUsersNumberResponse = connectedUsersNumber.execute();
		NumUserConnected connectedUsersNumberBody = connectedUsersNumberResponse.body();
		return connectedUsersNumberBody;
	}

	public String banUser(BanUser banUser) throws IOException {
		Call<String> register = ejabberdApi.banUser(banUser);
		Response<String> registerResponse = register.execute();
		String registerBody = registerResponse.body();
		System.out.println();
		return registerBody;
	}

	public String deleteOldUsers(DeleteOldUsers deleteTimeOldUsers) throws IOException {
		Call<String> register = ejabberdApi.deleteOldUsers(deleteTimeOldUsers);
		Response<String> registerResponse = register.execute();
		String registerBody = registerResponse.body();
		System.out.println();
		return registerBody;
	}

	public GetLastActivity getLastActivity(GetLast getLastUser) throws IOException {
		Call<GetLastActivity> getLastActivityCall = ejabberdApi.getLast(getLastUser);
		Response<GetLastActivity> getLastActivityResponse = getLastActivityCall.execute();
		GetLastActivity getLastActivity = getLastActivityResponse.body();
		return getLastActivity;
	}

	public ResponseOfflineCount getOfflineCount(GetOfflineCount getOfflineCount) throws IOException {
		Call<ResponseOfflineCount> offlineCount = ejabberdApi.getOfflineCount(getOfflineCount);
		Response<ResponseOfflineCount> offlineCountResponse = offlineCount.execute();
		ResponseOfflineCount offlineCountBody = offlineCountResponse.body();
		System.out.println();
		return offlineCountBody;
	}

	public String unregisterUser(Unregister unregisterUser) throws IOException{
		Call<String> unregister = ejabberdApi.unregisterUser(unregisterUser);
		Response<String> unregisterResponse = unregister.execute();
		String unregisteredUser = unregisterResponse.body();
		return unregisteredUser;
	}

	public String changePasswordUser(ChangePasswordUser changePasswordUser) throws IOException{
		Call<String> passwordChange = ejabberdApi.changePassword(changePasswordUser);
		Response<String> changePasswordResponse = passwordChange.execute();
		String changePaswordUser = changePasswordResponse.body();
		return changePaswordUser;
	}

	public String createRoom(CreateRoom createRoom)throws IOException{
	Call<String> create = ejabberdApi.createRoom(createRoom);
	Response<String> createRoomResponse = create.execute();
	String createStatus = createRoomResponse.body();
	if (createStatus.equals("0"))
		System.out.println("Room " + createRoom.getName() + " created");
	return createStatus;
}
	
	public List<RoomOccupants> getRoomOccupants(GetRoomOccupants getRoomOccupants) throws IOException{
		Call<List<RoomOccupants>> getRoomOccupantsCall = ejabberdApi.getRoomOccupants(getRoomOccupants);
		Response<List<RoomOccupants>> getRoomOccupantsResponse = getRoomOccupantsCall.execute();
		return getRoomOccupantsResponse.body();
	}

	public String accountCheck(UserCheck userCheck) throws IOException{
		Call<String> userCheckApi = ejabberdApi.accountCheck(userCheck);
		Response<String> userCheckResponse = userCheckApi.execute();
		if (userCheckResponse.body().equals("0"))
			System.out.println("User " + userCheck.getUser() + " exists!");
		return userCheckResponse.body();
	}

	public String passwordCheck(User user) throws IOException{
		Call<String> passwordCheckApi = ejabberdApi.passwordCheck(user);
		Response<String> passwordCheckResponse = passwordCheckApi.execute();
		return passwordCheckResponse.body();
	}

	public UnbanWrap unbanIp(UnbanIp unbanIp) throws IOException {
		Call<UnbanWrap> unbanIpApi = ejabberdApi.unbanIp(unbanIp);
		Response<UnbanWrap> unbanIpResponse = unbanIpApi.execute();
		return unbanIpResponse.body();
	}

}
