package it.ecubit.xmpp.services.rest;

import com.google.gson.Gson;
import it.ecubit.xmpp.services.exception.ExceptionGeneric;
import it.ecubit.xmpp.services.rest.entity.*;
import it.ecubit.xmpp.services.rest.entity.room.CreateRoom;
import it.ecubit.xmpp.services.rest.entity.room.GetRoomOccupants;
import it.ecubit.xmpp.services.rest.wrapperEntity.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
	//Lista degli utenti connessi
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

	public String banUser() throws IOException {
		BanUser banU = new BanUser("ugo", "localhost", "Too much Spam");
		Call<String> register = ejabberdApi.banUser(banU);
		Response<String> registerResponse = register.execute();
		String registerBody = registerResponse.body();
		System.out.println();
		return registerBody;
	}

	public String deleteOldUsers() throws IOException {
		DeleteOldUsers deleteOldUsers = new DeleteOldUsers( 1);
		Call<String> register = ejabberdApi.deleteOldUsers(deleteOldUsers);
		Response<String> registerResponse = register.execute();
		String registerBody = registerResponse.body();
		System.out.println();
		return registerBody;
	}

	public GetLastActivity getLastActivity() throws IOException {
		Call<GetLastActivity> getLastActivityCall = ejabberdApi.getLast(new GetLast("admin", "localhost"));
		Response<GetLastActivity> getLastActivityResponse = getLastActivityCall.execute();
		GetLastActivity getLastActivity = getLastActivityResponse.body();
		return getLastActivity;
	}

	public ResponseOfflineCount getOfflineCount(GetOfflineCount user) throws IOException {
		Call<ResponseOfflineCount> offlineCount = ejabberdApi.getOfflineCount(user);
		Response<ResponseOfflineCount> offlineCountResponse = offlineCount.execute();
		ResponseOfflineCount offlineCountBody = offlineCountResponse.body();
		System.out.println();
		return offlineCountBody;
	}

	public String unregisterUser() throws IOException{
		Unregister unregisterUsername = new Unregister("marco", "localhost");
		Call<String> unregister = ejabberdApi.unregisterUser(unregisterUsername);
		Response<String> unregisterResponse = unregister.execute();
		String unregisteredUser = unregisterResponse.body();
		return unregisteredUser;
	}

	public String changePasswordUser() throws IOException{
		ChangePasswordUser changePassword = new ChangePasswordUser("ugo", "localhost", "ugo1");
		Call<String> passwordChange = ejabberdApi.changePassword(changePassword);
		Response<String> changePasswordResponse = passwordChange.execute();
		String changePaswordUser = changePasswordResponse.body();
		return changePaswordUser;
	}

	public String createRoom(CreateRoom room)throws IOException{
	Call<String> create = ejabberdApi.createRoom(room);
	Response<String> createRoomResponse = create.execute();
	String createStatus = createRoomResponse.body();
		if(createStatus.equals("0"))
			createStatus = "Room creata con successo" + room;
		else createStatus = "Errore nella creazione della room";
	return createStatus;
}
	
	public List getRoomOccupants() throws IOException{
		GetRoomOccupants getRoomOccupants = new GetRoomOccupants("room1", "muc.localhost.com");
		Call<List> getRoomOccupantsCall = ejabberdApi.getRoomOccupants(getRoomOccupants);
		Response<List> getRoomOccupantsResponse = getRoomOccupantsCall.execute();
		List getRoomOccupantsBody = getRoomOccupantsResponse.body();
		return getRoomOccupantsBody;
	}

	public String accountCheck() throws IOException{
		UserCheck userCheck = new UserCheck("ugo", "localhost");
		Call<String> userCheckApi = ejabberdApi.accountCheck(userCheck);
		Response<String> userCheckResponse = userCheckApi.execute();
		return userCheckResponse.body();
	}

	public String passwordCheck() throws IOException{
		User user = new User("ugo", "localhost", "ugo1");
		Call<String> passwordCheckApi = ejabberdApi.passwordCheck(user);
		Response<String> passwordCheckResponse = passwordCheckApi.execute();
		return passwordCheckResponse.body();
	}

	public UnbanWrap unbanIp() throws IOException {
		UnbanIp unbanIp = new UnbanIp("localhost");
		Call<UnbanWrap> unbanIpApi = ejabberdApi.unbanIp(unbanIp);
		Response<UnbanWrap> unbanIpResponse = unbanIpApi.execute();
		return unbanIpResponse.body();
	}

}
