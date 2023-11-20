package it.ecubit.xmpp.services.rest.wrapperEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetLastActivity {

    String timestamp;
    String status;
}
