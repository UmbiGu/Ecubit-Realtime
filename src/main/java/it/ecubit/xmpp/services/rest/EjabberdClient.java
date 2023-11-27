package it.ecubit.xmpp.services.rest;

import com.google.gson.Gson;
import it.ecubit.xmpp.services.exception.BadRequestException;
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

import java.io.IOException;
import java.util.List;
import java.util.Objects;

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

	public List<UserInfo> connectedUsers() throws BadRequestException, IOException {
		Call<List<UserInfo>> userInfoList = ejabberdApi.getUserInfo();
		Response<List<UserInfo>> response = userInfoList.execute();
		List<UserInfo> userInfos = response.body();
		if (userInfos != null && userInfos.size() == 1) {
			throw new BadRequestException("Nessun utente connesso, oltre l'Admin.");
		}
		return userInfos;
	}

	public void registerUser(User user) throws BadRequestException, IOException {
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
				t.addSuppressed(new BadRequestException("Errore Generico"));
				t.addSuppressed(new IOException("Errore boh"));
			}
		});
	}

	public List<User> getRegisterUsers() throws IOException, BadRequestException {
		Host h = new Host("localhost");
		Call<List> register = ejabberdApi.getUsers(h);
		Response<List> registerResponse = register.execute();
		if (registerResponse.body().size() == 1)
			throw new BadRequestException("Nessun utente registrato, oltre l'Admin.");
		return registerResponse.body();
	}

	public NumUserConnected getConnectedUsersNumber() throws BadRequestException, IOException {
		Call<NumUserConnected> connectedUsersNumber = ejabberdApi.getConnectedUsersNumber();
		Response<NumUserConnected> connectedUsersNumberResponse = connectedUsersNumber.execute();
		NumUserConnected connectedUsersNumberBody = connectedUsersNumberResponse.body();
		if (connectedUsersNumberBody.getNum_sessions() == 1)
			throw new BadRequestException("Nessun utente connesso, oltre l'Admin.");
		return connectedUsersNumberBody;
	}

	public String banUser(BanUser banUser) throws BadRequestException, IOException {
		Call<String> register = ejabberdApi.banUser(banUser);
		Response<String> registerResponse = register.execute();
		String registerBody = registerResponse.body();
		if (!Objects.equals(registerBody, "0"))
			throw new BadRequestException("Errore nel ban dell'utente");
		return registerBody;
	}

	public String deleteOldUsers(DeleteOldUsers deleteTimeOldUsers) throws IOException, BadRequestException {
		Call<String> register = ejabberdApi.deleteOldUsers(deleteTimeOldUsers);
		Response<String> registerResponse = register.execute();
		String registerBody = registerResponse.body();
		if (!Objects.equals(registerBody, "0"))
			throw new BadRequestException("Errore nella cancellazione degli utenti");
		return registerBody;
	}

	public GetLastActivity getLastActivity(GetLast getLastUser) throws IOException, BadRequestException {
		Call<GetLastActivity> getLastActivityCall = ejabberdApi.getLast(getLastUser);
		Response<GetLastActivity> getLastActivityResponse = getLastActivityCall.execute();
		GetLastActivity getLastActivity = getLastActivityResponse.body();
		if (getLastActivity == null)
			throw new BadRequestException("Errore nella ricerca dell'ultima attività");
		return getLastActivity;
	}

	public ResponseOfflineCount getOfflineCount(GetOfflineCount getOfflineCount) throws IOException, BadRequestException {
		Call<ResponseOfflineCount> offlineCount = ejabberdApi.getOfflineCount(getOfflineCount);
		Response<ResponseOfflineCount> offlineCountResponse = offlineCount.execute();
		ResponseOfflineCount offlineCountBody = offlineCountResponse.body();
		return offlineCountBody;
	}

	public String unregisterUser(Unregister unregisterUser) throws IOException, BadRequestException {
		Call<String> unregister = ejabberdApi.unregisterUser(unregisterUser);
		Response<String> unregisterResponse = unregister.execute();
		String unregisteredUser = unregisterResponse.body();
		if (!Objects.equals(unregisteredUser, "0"))
			throw new BadRequestException("Errore nella cancellazione dell'utente");
		return unregisteredUser;
	}

	public String changePasswordUser(ChangePasswordUser changePasswordUser) throws IOException, BadRequestException {
		Call<String> passwordChange = ejabberdApi.changePassword(changePasswordUser);
		Response<String> changePasswordResponse = passwordChange.execute();
		String changePaswordUser = changePasswordResponse.body();
		if (!Objects.equals(changePaswordUser, "0"))
			throw new BadRequestException("Errore nella modifica della password");
		return changePaswordUser;
	}

	public String createRoom(CreateRoom createRoom) throws IOException, BadRequestException {
		Call<String> create = ejabberdApi.createRoom(createRoom);
		Response<String> createRoomResponse = create.execute();
		String createStatus = createRoomResponse.body();
		if (createStatus.equals("1"))
			throw new BadRequestException("Errore nella creazione della stanza");
		return createStatus;
	}
	
	public List<RoomOccupants> getRoomOccupants(GetRoomOccupants getRoomOccupants) throws IOException, BadRequestException {
		Call<List<RoomOccupants>> getRoomOccupantsCall = ejabberdApi.getRoomOccupants(getRoomOccupants);
		Response<List<RoomOccupants>> getRoomOccupantsResponse = getRoomOccupantsCall.execute();
		if (getRoomOccupantsResponse.body().size() == 0)
			throw new BadRequestException("Nessun occupante nella stanza");
		return getRoomOccupantsResponse.body();
	}

	public String accountCheck(UserCheck userCheck) throws IOException, BadRequestException {
		Call<String> userCheckApi = ejabberdApi.accountCheck(userCheck);
		Response<String> userCheckResponse = userCheckApi.execute();
		if (userCheckResponse.body().equals("1"))
			throw new BadRequestException("Utente non registrato");
		return userCheckResponse.body();
	}

	public String passwordCheck(User user) throws BadRequestException, IOException{
		Call<String> passwordCheckApi = ejabberdApi.passwordCheck(user);
		Response<String> passwordCheckResponse = passwordCheckApi.execute();
		if (passwordCheckResponse.body().equals("1")){
			throw new BadRequestException("Password errata");
		}
		return passwordCheckResponse.body();
	}

	public UnbanWrap unbanIp(UnbanIp unbanIp) throws IOException, BadRequestException {
		Call<UnbanWrap> unbanIpApi = ejabberdApi.unbanIp(unbanIp);
		Response<UnbanWrap> unbanIpResponse = unbanIpApi.execute();
		if (unbanIpResponse.body().getUnbanned().equals("0"))
			throw new BadRequestException("Errore nel rimuovere l'ip dalla blacklist");
		return unbanIpResponse.body();
	}

}
